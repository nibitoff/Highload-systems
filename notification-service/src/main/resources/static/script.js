var stompClient = null;
var notificationCount = 0;

var jwt = null
let isAuth = false
let userId = null

$('#wait-auth').hide();
updateNotificationDisplay()

$(document).ready(function() {
    console.log("Index page is ready");
    connect();

    $("#send").click(function() {
        sendMessage();
    });

    $("#send-private").click(function() {
        sendPrivateMessage();
    });

    $("#notifications").click(function() {
        resetNotificationCount();
    });
});

function connect() {
    var socket = new SockJS('http://localhost:7777/our-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        updateNotificationDisplay();

        stompClient.subscribe('/topic/sales', function (message) {
            showMessage(JSON.parse(message.body).content);
        });

        stompClient.subscribe('/topic/orders', function (message) {
            showMessage(message);
        });

//        stompClient.subscribe('/user/topic/private-messages', function (message) {
//            showMessage(JSON.parse(message.body).content);
//        });
//
//        stompClient.subscribe('/topic/global-notifications', function (message) {
//            notificationCount = notificationCount + 1;
//            updateNotificationDisplay();
//        });
//
//        stompClient.subscribe('/user/topic/private-notifications', function (message) {
//            notificationCount = notificationCount + 1;
//            updateNotificationDisplay();
//        });
    });
}

function showMessage(message) {
    if (isAuth) {
        order = JSON.parse(message.body)
        if (order.username == userId){
            notificationCount = notificationCount + 1;
            updateNotificationDisplay()
            $("#messages").append("<tr><td>" + order.cocktail.name + " for " + order.username + " has been prepared</td></tr>");
        }
    }
}

function sendMessage() {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': $("#message").val()}));
}

function sendPrivateMessage() {
    console.log("sending private message");
    stompClient.send("/ws/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
}

function updateNotificationDisplay() {
    if (notificationCount == 0) {
        $('#notifications').hide();
    } else {
        console.log("BAM")
        $('#notifications').show();
        $('#notifications').text(notificationCount);
    }
}

function resetNotificationCount() {
    notificationCount = 0;
    $("#messages").empty();
    updateNotificationDisplay();
}

$('button#auth').on('click', function(e) {
    e.preventDefault()
    logIn()
});

const logIn = () => {
    username  = $('#username').val()
    password  = $('#password').val()

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/auth/authenticate', true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    args = {
        "username": username,
        "password": password
    }
    xhr.send(JSON.stringify(args));
    xhr.responseType = 'json';
    $('#wait-auth').show();
    xhr.onload = function() {
        $('#wait-auth').hide();
        if (this.status === 200) {
            jwt = xhr.response.token
            isAuth = true
            userId = username
        } else {
            console.log("failed")
            jwt = null
            userId = null
            isAuth = false
        }
    };

//    fetchOrders()
}

const fetchOrders = () => {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/auth/authenticate', true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    args = {
        "username": username,
        "password": password
    }
    xhr.send(JSON.stringify(args));
    xhr.responseType = 'json';
    $('#wait-auth').show();
    xhr.onload = function() {
        $('#wait-auth').hide();
        if (this.status === 200) {
            jwt = xhr.response.token
        } else {
            console.log("failed")
        }
    };

    fetchOrders()
}
