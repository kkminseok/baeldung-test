function generateRandomUsername() {
    return 'user-' + crypto.randomUUID();
}

let currentSubscription = null;
let roomId = null;
const username = generateRandomUsername()

function sendImageMessage(imageUrl, roomId) {
    stompClient.publish({
        destination: "/app/chat/"+ roomId,
        body: JSON.stringify({
            type: "image",
            message: imageUrl,
            roomId: roomId
        })
    });
}

function getSelectedRoomId() {
    var roomId = document.getElementById("roomSelect").value;
    if(roomId == null){
        roomId = 1;
    }
    return roomId;
}

function subscribeToRoom(roomId) {
    // 이전 구독 해저
    if (currentSubscription) {
        currentSubscription.unsubscribe();
    }

    // 새 방 구독
    currentSubscription = stompClient.subscribe('/topic/chat.' + roomId, (response) => {
        console.log("새 방 메시지:", response);
        showGreeting(JSON.parse(response.body).ip + ": " + JSON.parse(response.body).message);
    });

    loadChatHistory(roomId);
    console.log("구독 완료: /topic/chat." + roomId);
}

function loadRoomList() {
    fetch("/api/chat/room/list")
        .then(response => response.json())
        .then(rooms => {
            const select = document.getElementById("roomSelect");
            // 초기화
            select.innerHTML = '<option value="">방을 선택하세요</option>';

            // 방 리스트 채우기
            rooms.forEach(roomId => {
                const option = document.createElement("option");
                option.value = roomId;
                option.text = "방 번호: " + roomId;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.error("방 목록을 불러오는 중 오류 발생:", error);
        });
}

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket',
    connectHeaders: {
        username: username,
        type: "enduser"
    }
});

stompClient.onConnect = (frame) => {
    setConnected(true);

    showGreeting("Bot: 안녕하세요. 상담원을 연결중입니다.")

    stompClient.subscribe('/queue/room.' + roomId, (response) => {
        const roomInfo = JSON.parse(response.body);
        roomId = roomInfo.roomId;
        console.log("roomId:", roomInfo);
        currentSubscription = stompClient.subscribe('/topic/chat.'+ roomInfo.roomId, (response) => {
            console.log(response)
            showGreeting(JSON.parse(response.body).dateTime + "/" + JSON.parse(response.body).ip + ": " + JSON.parse(response.body).message, JSON.parse(response.body).type);
        });

    })

    //이미 생성된 방 있으면 거기를 구독.

    //없는 경우 방생성
    console.log("나 방만든다")
    fetch('/api/chat/create-room/' + username, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })

    //loadChatHistory(1);
    console.log('Connected: ' + frame);

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
    $("#agree").prop("disabled", !connected);
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

function sendName(roomId) {
    stompClient.publish({
        destination: "/app/chat/"+ roomId,
        body: JSON.stringify({'message': $("#name").val(), 'roomId': roomId})
    });
}

function loadChatHistory(roomId) {
    $("#greetings").html("");
    fetch("/api/chat/rooms/" + roomId + "/messages")
        .then(response => response.json())
        .then(messages => {
            console.log("message! ", messages);
            messages.forEach(msg => {
                showGreeting(msg.dateTime + "/" + msg.ip + ": " + msg.message, msg.type);
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

function agree() {
    // 상담원을 찾고 해당 상담원과 같은 방을 구독
    console.log(roomId)

}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#agree").click(() => agree());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendName(getSelectedRoomId()));
    loadRoomList();
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

// 방 선택 시 액션 실행
document.getElementById("roomSelect").addEventListener("change", function () {
    const selectedRoom = this.value;
    console.log("선택된 방:", selectedRoom);

    if (selectedRoom) {
        subscribeToRoom(selectedRoom);
    }
});