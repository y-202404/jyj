package com.example.nestco.services;

import com.example.nestco.config.local.LocalUserDetails;
import com.example.nestco.dao.repository.NestcoItemsRepository;
import com.example.nestco.dao.repository.TransactionRepository;
import com.example.nestco.models.dto.ExchangeRequestDTO;
import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NestcoItems;
import com.example.nestco.models.entity.TransactionStatus;
import com.example.nestco.models.entity.Transactions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final TransactionRepository transactionRepository;
    private final NestcoItemsRepository itemRepository;

    // 교환 요청 처리
    @Transactional
    public void processExchangeRequest(ExchangeRequestDTO requestDTO) {
        Member requester = getCurrentUser();
        log.info("Processing Exchange Request - Uploader Item ID: {}, Exchange Item ID: {}", requestDTO.getUploaderItemId(), requestDTO.getExchangeItemId());

        NestcoItems uploaderItem = itemRepository.findById(requestDTO.getUploaderItemId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid uploader item ID"));
        NestcoItems exchangeItem = itemRepository.findById(requestDTO.getExchangeItemId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid exchange item ID"));

        Transactions transaction = new Transactions();
        transaction.setRequester(requester);
        transaction.setReceiver(uploaderItem.getUploader());  // 업로더를 수신자로 설정
        transaction.setUploaderItem(uploaderItem);
        transaction.setExchangeItem(exchangeItem);
        transaction.setMessage(requestDTO.getMessage());
        transaction.setAccepted(false);
        transaction.setStatus(TransactionStatus.PENDING);  // 초기 상태는 대기 중

        transactionRepository.save(transaction);  // 저장
    }

    // 교환 수락/거절 처리
    @Transactional
    public Transactions processExchangeResponse(Long requestId, boolean accept) {
        Transactions transaction = transactionRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));

        transaction.updateAccepted(accept);
        // boolean 값인 accept를 TransactionStatus로 변환
        TransactionStatus newStatus = accept ? TransactionStatus.PENDING : TransactionStatus.CANCELED;
        transaction.updateTransactionStatus(newStatus);
        return transactionRepository.save(transaction);
    }

    // 아이템 ID로 아이템 조회
    public NestcoItems getItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID: " + itemId));
    }

    // 교환 요청 ID로 요청 정보 조회
    public Transactions getExchangeRequestById(Long requestId) {
        return transactionRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request ID: " + requestId));
    }

    // 거래 상태 업데이트
    @Transactional
    public void updateTransactionStatus(Long transactionId, TransactionStatus status) {
        Transactions transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaction ID: " + transactionId));

        transaction.updateTransactionStatus(status);
        transactionRepository.save(transaction);
    }

    public NestcoItems getUploaderByItemId(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Item ID"));
    }

    public Transactions getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Transaction ID"));
    }

    private Member getCurrentUser() {
        return ((LocalUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMemberEntity();
    }
}

