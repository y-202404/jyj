//package com.example.nestco.config.socket.handler;
//
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.annotation.OnConnect;
//import com.corundumstudio.socketio.annotation.OnDisconnect;
//import com.example.nestco.models.dto.ExchangeRequestDTO;
//import com.corundumstudio.socketio.annotation.OnEvent;
//import com.example.nestco.dao.repository.TransactionRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class ExchangeRequestHandler {
//
//    private final SocketIOServer socketIOServer;
//    private final Map<String, UUID> userSocketMap = new ConcurrentHashMap<>();
//
//    // 클라이언트 연결 처리
//    @OnConnect
//    public void onConnect(SocketIOClient client) {
//        log.info("클라이언트 연결됨: {}", client.getSessionId());
//        // 클라이언트가 연결될 때 사용자 ID를 전달받아 매핑 (클라이언트에서 emit으로 전달)
//        client.sendEvent("requestUserId");
//    }
//
//    // 클라이언트 연결 종료 처리
//    @OnDisconnect
//    public void onDisconnect(SocketIOClient client) {
//        log.info("클라이언트 연결 해제됨: {}", client.getSessionId());
//        // 매핑된 사용자 ID 제거
//        userSocketMap.values().removeIf(uuid -> uuid.equals(client.getSessionId()));
//    }
//
//    @OnEvent("registerUser")
//    public void onRegisterUser(SocketIOClient client, String userId) {
//        userSocketMap.put(userId, client.getSessionId());
//        log.info("사용자 등록: {} -> {}", userId, client.getSessionId());
//    }
//
//    // 교환 요청 알림을 업로더와 요청자 모두에게 전송
//    @OnEvent("exchangeRequest")
//    public void onExchangeRequest(SocketIOClient client, ExchangeRequestDTO requestDTO) {
//        String requesterId = requestDTO.getRequesterId();
//        String receiverId = requestDTO.getReceiverId();
//
//        UUID receiverUUID = userSocketMap.get(receiverId);
//        UUID requesterUUID = userSocketMap.get(requesterId);
//
//        if (receiverUUID != null) {
//            socketIOServer.getClient(receiverUUID).sendEvent("newExchangeRequest", requestDTO);
//        }
//
//        if (requesterUUID != null) {
//            socketIOServer.getClient(requesterUUID).sendEvent("exchangeRequestSent", "교환 요청이 전송되었습니다.");
//        }
//
//        log.info("교환 요청: {} -> {}", requesterId, receiverId);
//    }
//
//    @OnEvent("exchangeResponse")
//    public void onExchangeResponse(SocketIOClient client, ExchangeRequestDTO responseDTO) {
//        String requestId = responseDTO.getRequesterId();
////        boolean accepted = responseDTO.isAccepted();
//
//        // 요청자와 수신자의 ID를 기반으로 알림 전송
//        // 예시로 requestId를 요청자의 ID로 가정
//        UUID requesterUUID = userSocketMap.get(requestId);
//        UUID receiverUUID = client.getSessionId();
//
////        String responseMessage = accepted ? "교환이 수락되었습니다." : "교환이 거절되었습니다.";
//
//        if (requesterUUID != null) {
//            socketIOServer.getClient(requesterUUID).sendEvent(accepted ? "exchangeAccepted" : "exchangeRejected", responseMessage);
//        }
//
//        log.info("교환 응답: {} 수락 여부: {}", requestId, accepted);
//    }
//
//
//    // 교환 응답 알림을 양쪽 사용자에게 전송
//    public void sendExchangeResponseNotification(String requesterId, String receiverId, boolean accepted) {
//        UUID requesterUUID = UUID.fromString(requesterId);
//        UUID receiverUUID = UUID.fromString(receiverId);
//
//        String responseMessage = accepted ? "교환이 수락되었습니다." : "교환이 거절되었습니다.";
//
//        socketIOServer.getClient(requesterUUID).sendEvent("exchangeResponse", responseMessage);  // 요청자에게 알림
//        socketIOServer.getClient(receiverUUID).sendEvent("exchangeResponse", responseMessage);  // 업로더에게도 알림
//    }
//
//    // 거래 상태 알림
//    public void sendTransactionStatusNotification(String requesterId, String receiverId, String statusMessage) {
//        UUID requesterUUID = UUID.fromString(requesterId);
//        UUID receiverUUID = UUID.fromString(receiverId);
//
//        socketIOServer.getClient(requesterUUID).sendEvent("transactionStatus", statusMessage);  // 요청자에게 알림
//        socketIOServer.getClient(receiverUUID).sendEvent("transactionStatus", statusMessage);  // 업로더에게도 알림
//    }
//
//    public void sendChatEndNotification(String requesterId, String receiverId, String message) {
//        UUID requesterUUID = UUID.fromString(requesterId);  // String을 UUID로 변환
//        UUID receiverUUID = UUID.fromString(receiverId);  // String을 UUID로 변환
//        socketIOServer.getClient(requesterUUID).sendEvent("chatEnded", message);  // 요청자에게 알림
//        socketIOServer.getClient(receiverUUID).sendEvent("chatEnded", message);
//    }
//}
