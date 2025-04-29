const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);

    loadChatHistory(1);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/chat.1', (response) => {
        console.log(response)
        showGreeting(JSON.parse(response.body).ip + ": " + JSON.parse(response.body).message);
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
    }
    else {
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
                showGreeting(msg.ip + ": " + msg.message);
            });
        });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}


$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});