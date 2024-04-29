// JS
const modalOverlay = document.querySelector("#modal-overlay");
const modal = document.querySelector("#modal");
const modalBtn = document.querySelector("#modal-btn");
const modalClose = document.querySelector("#modal-close");

function openAuthModal(event){
    event.preventDefault();
    modalOverlay.style.display = "inline";
    modal.style.display = "block";
    document.querySelector('body').style.overflow = "hidden";
}

modalClose.addEventListener("click", () => {
    modalOverlay.style.display = "none";
    modal.style.display = "none";
    clearForm();
    document.querySelector('body').style.overflow = "auto";
});

window.onclick = function(event) {

    if (event.target === modalOverlay) {
        modalOverlay.style.display = "none";
        modal.style.display = "none";
        document.querySelector('body').style.overflow = "auto";
        clearForm();
    }
}

function clearForm(){
    document.querySelector('#username').value='';
    document.querySelector('#password').value='';
    document.querySelector('#regusername').value='';
    document.querySelector('#regfirstname').value='';
    document.querySelector('#reglastname').value='';
    document.querySelector('#regemail').value='';
    document.querySelector('#regpassword').value='';
}

const formSignUp = document.getElementById('signup-form');
const formLogin = document.getElementById('login-form');
formSignUp.addEventListener('submit', sendRequestAuth);
formLogin.addEventListener('submit', sendRequestAuth);
function sendRequestAuth(event) {
    event.preventDefault(); // Prevent the form from submitting normally
    const form = event.target;
    console.log(form);
    const formData = new FormData(form); // Get the form data
    console.log(formData);
    const url = form.getAttribute('action');
    fetch(form.action, { // Send an AJAX request to the server
        method: form.method,
        body: JSON.stringify(Object.fromEntries(formData)),
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json()) // Parse the response as JSON
        .then(data => {
        if(data.statusCode!==400) {

           location.reload();
        } else {
        document.querySelector('#message').textContent = data.message;
                    clearForm();
                    console.log(document.querySelector('#message'))
                    document.querySelector('#overlay-message').style.display = 'block';
                    document.querySelector('#modal-message').style.display = 'block';
                    console.error(data); // Handle any errors that occur during the AJAX request
        }
        })
        .catch((error, data) => {
            document.querySelector('#message').textContent = data.message;
            clearForm();
            console.log(document.querySelector('#message'))
            document.querySelector('#overlay-message').style.display = 'block';
            document.querySelector('#modal-message').style.display = 'block';
            console.error(data); // Handle any errors that occur during the AJAX request
        });
}
const closeModalButton = document.querySelector('#close-modal-message');
closeModalButton.addEventListener('click', function() {
    document.querySelector('#overlay-message').style.display = 'none';
    document.querySelector('#modal-message').style.display = 'none';


});

function logOut(event){
    event.preventDefault();
    const link = event.target;
    const url = link.getAttribute('href');
    fetch(url)
        .then(response =>response.json())
        .then(data => {
            window.location.href = data.redirect;
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}