function sendImageMessage(imageUrl) {
    stompClient.publish({
        destination: "/app/chat/1",
        body: JSON.stringify({
            type: "image",
            message: imageUrl,
            roomId: 1
        })
    });
}

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);

    loadChatHistory(1);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/chat.1', (response) => {
        console.log(response)
        showGreeting(JSON.parse(response.body).dateTime + "/" + JSON.parse(response.body).ip + ": " + JSON.parse(response.body).message, JSON.parse(response.body).type);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.publish({
        destination: "/app/chat/1",
        body: JSON.stringify({'message': $("#name").val(), 'roomId': 1})
    });
}

function loadChatHistory(roomId) {
    fetch("/api/chat/rooms/" + roomId + "/messages")
        .then(response => response.json())
        .then(messages => {
            console.log("message! ", messages);
            messages.forEach(msg => {
                showGreeting(msg.dateTime + "/" + msg.ip + ": " + msg.message, "text");
            });
        });
}

function showGreeting(message, type) {
    let html;
    if (type === 'image') {
        html = '<tr><td><img src="https://kr1-api-object-storage.nhncloudservice.com/v1/AUTH_226f908c769e48b0bad7d32f9a91717f/survey_alpha/WopqM8euoYw89B7i/csatdev/%E1%84%8C%E1%85%A5%E1%86%AB%E1%84%8B%E1%85%B5%E1%86%B7.jpg" style="max-width: 200px;" /></td></tr>'
    } else {
        html = `<tr><td>${message}</td></tr>`;
    }

    $("#greetings").append(html);
}


$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName());
});

document.addEventListener('paste', (event) => {
    const items = (event.clipboardData || event.originalEvent.clipboardData).items;
    for (const item of items) {
        if (item.type.indexOf('image') === 0) {
            const file = item.getAsFile();
            const formData = new FormData();
            formData.append('file', file);

            // 서버에 업로드 요청
            fetch('/api/chat/upload', {
                method: 'POST',
                body: formData
            })
                .then(res => res.json())
                .then(data => {
                    const imageUrl = data.url; // 서버에서 반환한 이미지 URL
                    sendImageMessage(imageUrl); // STOMP로 이미지 URL 보내기
                });
        }
    }
});