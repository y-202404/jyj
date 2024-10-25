package com.example.nestco.controller;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.models.dto.ExchangeRequestDTO;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.models.entity.TransactionStatus;
import com.example.nestco.models.entity.Transactions;
import com.example.nestco.services.BaseService;
import com.example.nestco.services.ExchangeService;
import com.example.nestco.services.NestcoItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;
    private final NestcoItemsService itemsService;
    private final BaseService baseService;
    private final SimpMessagingTemplate messagingTemplate;


    @GetMapping("/getExchangeRequestData")
    public ResponseEntity<Map<String, Object>> getExchangeRequestData(@AuthenticationPrincipal LocalUserDetails localUserDetails, @RequestParam Long itemId) {

        if (localUserDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);  // 인증되지 않은 경우 401 응답
        }

        // 요청한 아이템 정보 가져오기
        NestcoItems uploaderItem = exchangeService.getItemById(itemId);

        // 로그인된 사용자의 아이템 목록 가져오기
        List<NestcoItems> myItems = itemsService.getItemsByUser(localUserDetails);

        // DTO 변환
        List<ExchangeRequestDTO> myItemDTOs = myItems.stream()
                .map(item -> new ExchangeRequestDTO(
                        localUserDetails.getMemberEntity().getUserId(),
                        localUserDetails.getMemberEntity().getNickname(),
                        uploaderItem.getUploader().getUserId(),
                        uploaderItem.getId(),
                        uploaderItem.getTitle(),
                        item.getId(),
                        item.getTitle(),
                        null,
                        false))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("uploaderItem", new ExchangeRequestDTO(
                localUserDetails.getMemberEntity().getUserId(),
                localUserDetails.getMemberEntity().getNickname(),
                uploaderItem.getUploader().getUserId(),
                uploaderItem.getId(),
                uploaderItem.getTitle(),
                null,
                null,
                null,
                false));
        response.put("myItems", myItemDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);  // JSON 형태로 반환
    }
    // 교환 요청 제출 처리
    @PostMapping("/submitExchangeRequest")
    public String submitExchangeRequest( @AuthenticationPrincipal LocalUserDetails localUserDetails,
                                         @RequestParam("uploaderItemId") Long uploaderItemId,
                                         @RequestParam("exchangeItemId") Long exchangeItemId,
                                         @RequestParam("message") String message,
                                         Model model) {

        baseService.headerAttributes(model);
        if (localUserDetails == null) {
            return "redirect:/loginForm";  // 로그인되지 않은 사용자는 로그인 페이지로 리다이렉트
        }
        NestcoItems uploaderItem = exchangeService.getItemById(uploaderItemId);
        String receiverId = uploaderItem.getUploader().getUserId();

        // 교환 요청 DTO 생성
        ExchangeRequestDTO requestDTO = new ExchangeRequestDTO();
        requestDTO.setUploaderItemId(uploaderItemId);
        requestDTO.setExchangeItemId(exchangeItemId);
        requestDTO.setReceiverId(receiverId);
        requestDTO.setMessage(message);
        requestDTO.setRequesterName(localUserDetails.getMemberEntity().getNickname());
        requestDTO.setRequesterId(localUserDetails.getMemberEntity().getUserId());
        requestDTO.setAccepted(false);

        log.info("Processing Exchange Request - Uploader Item ID: {}, Exchange Item ID: {}", uploaderItemId, exchangeItemId);

        // 교환 요청 처리
        exchangeService.processExchangeRequest(requestDTO);

        messagingTemplate.convertAndSendToUser(
                receiverId,
                "/queue/exchangeRequest",
                requestDTO
        );
        model.addAttribute("message", "교환 요청이 성공적으로 전송되었습니다.");
        return "/logined/exchangeResult"; // 교환 요청 결과 페이지
    }

    // 업로더의 교환 요청 응답 폼 페이지
    @GetMapping("/respondExchangeForm")
    public String getRespondExchangeForm(Model model, @RequestParam Long requestId) {
        Transactions transaction = exchangeService.getExchangeRequestById(requestId);

        // 요청자의 닉네임과 교환 물품 정보 전달
        model.addAttribute("uploaderItem", transaction.getUploaderItem().getTitle());
        model.addAttribute("exchangeRequest", transaction);
        model.addAttribute("requesterNickname", transaction.getRequester().getNickname());
        model.addAttribute("exchangeItem", transaction.getExchangeItem().getTitle());
        return "respondExchangeForm";  // 교환 응답 폼 렌더링
    }

    // 교환 수락/거절 처리
    @PostMapping("/respondToExchangeRequest")
    public String respondToExchangeRequest(@RequestParam Long requestId,
                                           @RequestParam boolean accept,
                                           @AuthenticationPrincipal LocalUserDetails localUserDetails,
                                           Model model) {
        Transactions transaction = exchangeService.processExchangeResponse(requestId, accept);  // 교환 응답 처리

        String requesterId = transaction.getRequester().getUserId();
        String message;

        if (accept) {
            message = "교환 요청이 수락되었습니다.";
            // 채팅 시작 알림 전송
            messagingTemplate.convertAndSendToUser(
                    requesterId,
                    "/queue/exchangeResponse",
                    message
            );
            model.addAttribute("message", "교환이 수락되었습니다.");
            return "chatStart";  // 채팅 페이지로 이동
        } else {
            message = "교환 요청이 거절되었습니다.";
            // 교환 거절 알림 전송
            messagingTemplate.convertAndSendToUser(
                    requesterId,
                    "/queue/exchangeResponse",
                    message
            );
            model.addAttribute("message", "교환이 거절되었습니다.");
            return "/logined/exchangeResult";
        }
    }

    // 거래 완료 처리
    @PostMapping("/completeTransaction/{transactionId}")
    public String completeTransaction(@RequestParam Long transactionId, @RequestParam boolean complete, Model model) {
        exchangeService.updateTransactionStatus(transactionId, complete ? TransactionStatus.COMPLETED : TransactionStatus.CANCELED);   // 거래 완료 처리

        Transactions transaction = exchangeService.getTransactionById(transactionId);
        String message = complete ? "거래가 완료되었습니다." : "거래가 취소되었습니다.";

        // 거래 완료 알림 전송
        messagingTemplate.convertAndSendToUser(
                transaction.getRequester().getUserId(),
                "/queue/transactionCompletion",
                message
        );
        messagingTemplate.convertAndSendToUser(
                transaction.getReceiver().getUserId(),
                "/queue/transactionCompletion",
                message
        );
        model.addAttribute("message", message);
        return "/logined/confirmation";  // 결과 페이지 반환
    }
}
