const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws',
    connectHeaders: {
        username: "user.123"  // 🔥 서버가 인식할 username
    }
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);

    // rabbitmq를 브로드캐스팅 용으로 쓰면 안되는듯
    // stompClient.subscribe('/user/queue/messages', (message) => {
    //     console.log("Private message: " + message.body);
    // });

    stompClient.subscribe("/queue/user.123", (message) => {
        console.log("Private123 message: " + message.body)
    })
};

stompClient.activate();

// 메시지 전송 함수
function sendPrivateMessage() {
    stompClient.publish({
        destination: "/app/chat.send",
        body: JSON.stringify({
            sender: "user.123",
            content: "Hello there!",
            receiver: "user.456"  // 🔥 받을 대상 유저
        })
    });
}