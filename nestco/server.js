// 필요한 모듈 불러오기
const express = require('express');
const http = require('http');
const socketIo = require('socket.io');
const path = require('path');

// Express 애플리케이션 설정
const app = express();
const server = http.createServer(app);
const io = require('socket.io')(server, {
               cors: {
                       origin: "*",  // 모든 도메인에서 접근을 허용 (원하는 경우 localhost:8095만 허용 가능)
                       methods: ["GET", "POST"]
                   }
           });

// 정적 파일 제공 (public 폴더에서 파일 제공)
app.use(express.static(path.join(__dirname, 'static')));
// Socket.IO 연결 처리
io.on('connection', (socket) => {
    console.log('A user connected:', socket.id);



    // 교환 요청 처리
    socket.on('exchangeRequest', (message, uploaderId, requesterId) => {
        console.log('교환 요청 발생:', message, '업로더 ID:', uploaderId, '요청자 ID:', requesterId);

        // 업로더에게 알림 전송
        io.to(uploaderId).emit('newNotification', `새로운 교환 요청이 도착했습니다: ${message}`);
    });

    // 교환 응답 처리 (수락 또는 거절)
    socket.on('exchangeResponse', (requestId, accept) => {
        const responseMessage = accept ? '교환 요청이 수락되었습니다.' : '교환 요청이 거절되었습니다.';
        console.log('교환 응답 처리:', requestId, '수락 여부:', accept);

        // 요청자에게 응답 전송
        io.to(requestId).emit('newNotification', responseMessage);
    });

    // 채팅 메시지 처리
    socket.on('chatMessage', (message) => {
        console.log('채팅 메시지:', message);
        // 모든 클라이언트에게 메시지 전송
        io.emit('chatMessage', message);
    });

    // 채팅 종료 처리
    socket.on('chatEnd', (requestId) => {
        console.log('채팅 종료:', requestId);
        io.to(requestId).emit('chatEnded');
    });

    // 거래 완료/미완료 처리
    socket.on('transactionStatus', (transactionId, completed) => {
        const statusMessage = completed ? '거래가 완료되었습니다.' : '거래가 미완료되었습니다.';
        console.log('거래 상태 업데이트:', transactionId, '완료 여부:', completed);
        io.to(transactionId).emit('transactionStatusUpdate', statusMessage);
    });

    // 클라이언트가 연결 해제할 때
        socket.on('disconnect', () => {
            console.log('User disconnected:', socket.id);
        });
});

// 서버 시작
const PORT = process.env.PORT || 3001;
server.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
