const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws',
    connectHeaders: {
        username: "user.456"  // 🔥 서버가 인식할 username
    }
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);

    // 특정 유저용 메시지 구독

    // rabbitmq를 브로드캐스팅 용으로 쓰면 안되는듯
    stompClient.subscribe('/user/queue/messages', (message) => {
        console.log("Private message: " + message.body);
    });

    // stompClient.subscribe("/queue/user.456", (message) => {
    //     console.log("Private456 message: " + message.body)
    // })
};

stompClient.activate();

// 메시지 전송 함수
function sendPrivateMessage() {
    stompClient.publish({
        destination: "/app/chat.send",
        body: JSON.stringify({
            sender: "user.456",
            content: "Hello there!",
            receiver: "user.123"  // 🔥 받을 대상 유저
        })
    });
}