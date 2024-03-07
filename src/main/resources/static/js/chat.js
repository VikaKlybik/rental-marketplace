'use strict';
const options = {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit', // Display hours with leading zeros (01-12)
    minute: '2-digit', // Display minutes with leading zeros (00-59)
    second: '2-digit' // Display seconds with leading zeros (00-59)
};

const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const chatArea = document.querySelector('#chat-messages');
const chatWith = document.querySelector('#chatWith')

let stompClient = Stomp.over(new SockJS('/rental-properties/ws'));
let userId = parseInt(document.querySelector("#userId").value);
let selectedUserId = null;

stompClient.connect({}, onConnected, onError);

function onConnected(options) {
    stompClient.subscribe(`/user/${userId}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/public`, onMessageReceived);

    // // register the connected user
    // stompClient.send("/app/user.addUser",
    //     {},
    //     JSON.stringify({nickName: nickname, fullName: fullname, status: 'ONLINE'})
    // );
    // document.querySelector('#connected-user-fullname').textContent = fullname;
    findAndDisplayChats().then();
}

async function findAndDisplayChats() {
    const chatsResponse = await fetch('/rental-properties/chats');
    let chats = await chatsResponse.json();
    const chatsList = document.getElementById('chats');
    chatsList.innerHTML = '';

    chats.forEach(chat => {
        appendUserElement(chat, chatsList);
        if (chats.indexOf(chat) < chats.length - 1) {
            const separator = document.createElement('li');
            separator.classList.add('separator');
            chatsList.appendChild(separator);
        }
    });
}

// <li className="clearfix">
//     <img alt="avatar" src="https://bootdey.com/img/Content/avatar/avatar1.png">
//         <div className="about">
//             <div className="name">Vincent Porter</div>
//         </div>
// </li>

function appendUserElement(chat, connectedUsersList) {
    const listItem = document.createElement('li');
    listItem.className = 'clearfix';
    listItem.id = chat.withUserId;

    const userImage = document.createElement('img');
    userImage.src = '/rental-properties/image/' + chat.userIcon;
    userImage.alt = chat.title;

    const divChatAbout = document.createElement('div');
    divChatAbout.className = 'about';
    const divChatInfo = document.createElement('div');
    divChatInfo.className = 'name';
    divChatInfo.textContent = chat.title;

    const receivedMsgs = document.createElement('span');
    receivedMsgs.textContent = '0';
    receivedMsgs.classList.add('nbr-msg', 'hidden');


    listItem.appendChild(userImage);
    divChatAbout.appendChild(divChatInfo);
    listItem.appendChild(divChatAbout);
    listItem.appendChild(receivedMsgs)

    listItem.addEventListener('click', userItemClick);

    connectedUsersList.appendChild(listItem);
}

function userItemClick(event) {
    document.querySelectorAll('#chats ul li.clearfix').forEach(item => {
        item.classList.remove('active');
    });
    messageForm.classList.remove('hidden');

    const clickedUser = event.currentTarget;
    clickedUser.classList.add('active');

    selectedUserId = parseInt(clickedUser.getAttribute('id'));
    const imageSrc = clickedUser.querySelector('img').src;
    const title = clickedUser.querySelector('div div').textContent;
    createChatInfo(selectedUserId, imageSrc, title);
    fetchAndDisplayUserChat().then();

    const nbrMsg = clickedUser.querySelector('.nbr-msg');
    nbrMsg.classList.add('hidden');
    nbrMsg.textContent = '0';

}

// <li className="clearfix">
//     <div className="message-data text-right">
//         <span className="message-data-time">10:10 AM, Today</span>
//         <img alt="avatar" src="https://bootdey.com/img/Content/avatar/avatar7.png">
//     </div>
//     <div className="message other-message float-right"> Hi Aiden, how are you? How is
//         the project coming along?
//     </div>
// </li>
// <li className="clearfix">
//     <div className="message-data">
//         <span className="message-data-time">10:15 AM, Today</span>
//     </div>
//     <div className="message my-message">Project has been already finished and I have
//         results to show you.
//     </div>
// </li>
function displayMessage(time, message, senderId) {
    const messageContainer = document.createElement('li');
    messageContainer.className = 'clearfix';

    const messageInfo = document.createElement('div')
    messageInfo.classList.add('message-data');

    const timeSpan = document.createElement('span');
    timeSpan.textContent = time.toLocaleDateString('ru-RU', options);
    timeSpan.classList.add('message-data-time');
    messageInfo.appendChild(timeSpan);

    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message');

    if (senderId === userId) {
        messageInfo.classList.add('text-right');
        messageDiv.classList.add('other-message','float-right')
    } else {
        messageDiv.classList.add('my-message')
    }
    messageDiv.textContent = message;
    messageContainer.appendChild(messageInfo);
    messageContainer.appendChild(messageDiv);

    chatArea.appendChild(messageContainer);
    let divMessage = document.getElementById('chat-history');
    divMessage.scrollTop = divMessage.scrollHeight;
}

async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/rental-properties/messages/${userId}/${selectedUserId}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';
    userChat.forEach(message => {
        displayMessage(new Date(message.time), message.message, message.senderId );
    });
    chatArea.scrollTop = chatArea.scrollHeight;
}


function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            senderId: userId,
            recipientId: selectedUserId,
            message: messageInput.value.trim(),
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        displayMessage(new Date(), messageInput.value.trim(), userId);
        messageInput.value = '';
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}


async function onMessageReceived(payload) {
    await findAndDisplayChats();
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);
    if (selectedUserId && selectedUserId === message.senderId) {
        displayMessage(new Date(message.time), message.message, selectedUserId);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    if (selectedUserId) {
        let element = document.getElementById(selectedUserId.toString());
        element.classList.add('active')
    } else {
        messageForm.classList.add('hidden');
    }

    const notifiedUser = document.getElementById(message.senderId.toString());
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        let oldValue = nbrMsg.textContent;
        if(oldValue){
            nbrMsg.textContent = (parseInt(oldValue)+1).toString();
        } else {
            nbrMsg.textContent = "0"
        }
    }
}

function createChatInfo(userId, imageSrc, title) {
    // Create a new div element with the class "row"
    var row = document.createElement("div");
    row.classList.add("row");

// Create a new div element with the class "col-lg-6"
    var col = document.createElement("div");
    col.classList.add("col-lg-6");

// Create an anchor element
    var anchor = document.createElement("a");
    anchor.href = "/rental-properties/user/"+userId; // Set href attribute to an empty string

// Create an image element
    var img = document.createElement("img");
    img.alt = "avatar"; // Set alt attribute
    img.src = imageSrc; // Set src attribute with the image URL

// Append the image element to the anchor element
    anchor.appendChild(img);

// Create a div element with the class "chat-about"
    var chatAbout = document.createElement("div");
    chatAbout.classList.add("chat-about");

// Create an h6 element with the text "Aiden Chavez"
    var h6 = document.createElement("h6");
    h6.classList.add("m-b-0");
    h6.textContent = title;

// Append the h6 element to the "chat-about" div
    chatAbout.appendChild(h6);

// Append the anchor element and "chat-about" div to the "col-lg-6" div
    col.appendChild(anchor);
    col.appendChild(chatAbout);

// Append the "col-lg-6" div to the "row" div
    row.appendChild(col);

// Append the "row" div to the document body or any other parent element
    chatWith.innerHTML='';
    chatWith.appendChild(row)
}
function submitGenerate(event){
    sendMessage(event)
}

messageForm.addEventListener('submit', sendMessage, true);