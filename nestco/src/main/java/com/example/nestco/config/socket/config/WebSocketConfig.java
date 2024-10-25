package com.example.nestco.config.socket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * Socket.IO 서버를 설정하고 서버의 기본적인 동작을 관리
 * args[?] : 클라이언트가 이벤트를 서버로 보낼 때 여러 데이터를 함께 보낼 수 있으며, args 배열을 통해 이러한 데이터를 서버에서 순서대로 받아서 사용할 수 있음
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지 브로커 구성
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트에게 전달되는 메시지의 prefix 설정
        config.enableSimpleBroker("/topic", "/queue");
        // 서버로 전송되는 메시지의 prefix 설정
        config.setApplicationDestinationPrefixes("/app");
        // 사용자별 메세지 전달을 위한 prefix 설정
        config.setUserDestinationPrefix("/user");
    }

    // STOMP 엔드포인트 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // WebSocket 연결을 위한 엔드포인트
                .setAllowedOrigins("*") // CORS 허용 (필요에 따라 수정 가능)
                .withSockJS(); // SockJS 사용 (WebSocket 미지원 브라우저 대비)
    }
}
