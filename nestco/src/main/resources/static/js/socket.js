// socket 연결 설정
const socket = io.connect('http://localhost:3001', {
                              transports: ['websocket', 'polling'],
                          });
// 교환 신청 이벤트 처리
function requestExchange(uploaderId, requesterId, message) {
    socket.emit('exchangeRequest', message, uploaderId, requesterId);
}

// 서버로부터 새로운 교환 요청 알림 수신
socket.on('newExchangeRequest', function (message) {
    alert("새로운 교환 요청이 도착했습니다: " + message);
    window.location.href = '/respondExchangeForm'; // 응답 폼으로 이동
});

// 교환 수락/거절 처리
function respondToExchangeRequest(requestId, accept) {
    socket.emit('exchangeResponse', requestId, accept);
}

// 교환 수락 처리 후 채팅 창 열기
socket.on('exchangeAccepted', function (message) {
    alert("교환이 수락되었습니다. 채팅을 시작합니다.");
    openChatWindow();
});

// 교환 거절 처리 후 알림
socket.on('exchangeRejected', function (message) {
    alert("교환이 거절되었습니다: " + message);
});

// 채팅 종료 처리
function endChat(requestId) {
    socket.emit('chatEnd', requestId);
}

// 채팅 종료 후 거래 상태 선택 폼
socket.on('chatEnded', function () {
    alert("채팅이 종료되었습니다. 거래 상태를 선택하세요.");
    document.getElementById('transactionStatusForm').style.display = 'block';
});

// 거래 완료/미완료 처리
function updateTransactionStatus(transactionId, completed) {
    socket.emit('transactionStatus', transactionId, completed);
    if (completed) {
        alert("거래가 완료되었습니다.");
    } else {
        alert("거래가 미완료되었습니다.");
    }
}

// 거래 완료/미완료 상태 업데이트 알림
socket.on('transactionStatusUpdate', function (message) {
    alert(message);
});

// 채팅 메시지 보내기
function sendMessage() {
    const message = document.getElementById('messageInput').value;
    if (message.trim() !== "") {
        socket.emit('chatMessage', message);
        document.getElementById('messageInput').value = '';  // 입력 필드 초기화
    }
}

// 채팅 메시지 받기
socket.on('chatMessage', function (message) {
    const messageContainer = document.getElementById('messages');
    const newMessage = document.createElement('div');
    newMessage.textContent = message;
    messageContainer.appendChild(newMessage);
});
