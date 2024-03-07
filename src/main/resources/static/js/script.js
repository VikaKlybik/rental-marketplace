(function ($) {
    "use strict";

    // Dropdown on mouse hover
    $(document).ready(function () {
        function toggleNavbarMethod() {
            if ($(window).width() > 992) {
                $('.navbar .dropdown').on('mouseover', function () {
                    $('.dropdown-toggle', this).trigger('click');
                }).on('mouseout', function () {
                    $('.dropdown-toggle', this).trigger('click').blur();
                });
            } else {
                $('.navbar .dropdown').off('mouseover').off('mouseout');
            }
        }
        toggleNavbarMethod();
        $(window).resize(toggleNavbarMethod);
    });


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Team carousel
    $(".team-carousel, .related-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        center: true,
        margin: 30,
        dots: false,
        loop: true,
        nav : true,
        navText : [
            '<i class="fa fa-angle-left" aria-hidden="true"></i>',
            '<i class="fa fa-angle-right" aria-hidden="true"></i>'
        ],
        responsive: {
            0:{
                items:1
            },
            992:{
                items:2
            }
        }
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        margin: 30,
        dots: true,
        loop: true,
        center: true,
        responsive: {
            0:{
                items:1
            },
            992:{
                items:2
            }
        }
    });

})(jQuery);

function makeBooking(event){
    event.preventDefault(); // Prevent the form from submitting normally
    let form = event.target;
    let sendDate = {
        startDate: form.elements['startDate'].value +"T"+form.elements['startTime'].value+":00",
        endDate: form.elements['endDateValue'].value+"T"+form.elements['startTime'].value+":00",
        carId: form.elements['carId'].value,
        username: form.elements['username'].value,
    }
    console.log(sendDate);
    const url = form.getAttribute('action');
    fetch(form.action, { // Send an AJAX request to the server
        method: form.method,
        body: JSON.stringify(sendDate),
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json()) // Parse the response as JSON
        .then(data => {
            document.querySelector('#message').textContent = "Автомобиль успешно забронирован";
            document.querySelector('#comment').value='';
            document.querySelector('#rating').selectedIndex=0;
            document.querySelector('#overlay-message').style.display = 'block';
            document.querySelector('#modal-message').style.display = 'block';
        })
        .catch(error => {
            document.querySelector('#message').textContent = "Автомобиль не может быть забронирован на данное время";
            document.querySelector('#comment').value='';
            document.querySelector('#rating').selectedIndex=0;
            document.querySelector('#overlay-message').style.display = 'block';
            document.querySelector('#modal-message').style.display = 'block';
            console.error(error); // Handle any errors that occur during the AJAX request
        });
}

function createMessage(data){
    document.querySelector('#message').textContent = "Автомобиль успешно забронирован";
    document.querySelector('#comment').value='';
    document.querySelector('#rating').selectedIndex=0;
    document.querySelector('#overlay-message').style.display = 'block';
    document.querySelector('#modal-message').style.display = 'block';
}


function cancelRental(event){
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
            document.querySelector('#message').textContent = data.message;
            document.querySelector('#overlay-message').style.display = 'block';
            document.querySelector('#modal-message').style.display = 'block';
            if(data.status === 201) {
                const form = event.target;
                form.parentNode.parentNode.querySelector('.status-type').textContent='Отменена';
                form.remove();
            }
        })
        .catch(error => {
            console.error(error); // Handle any errors that occur during the AJAX request
        });
}

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
    })
        .then(response => response.json()) // Parse the response as JSON
        .then(data => {
            location.reload();
        })
        .catch(error => {
            document.querySelector('#message').textContent = "Неверный логин/пароль";
            clearForm();
            console.log(document.querySelector('#message'))
            document.querySelector('#overlay-message').style.display = 'block';
            document.querySelector('#modal-message').style.display = 'block';
            console.error(error); // Handle any errors that occur during the AJAX request
        });
}

function sortCar(event){
    const paramName="sortValue";
    const paramValue = event.target.value;
    const currentUrl = window.location.href;

    // Создаем объект URL с текущим URL
    const url = new URL(currentUrl);

    // Устанавливаем новый параметр в URL
    url.searchParams.set(paramName, paramValue);

    // Перезагружаем страницу с обновленным URL
    window.location.href = url.toString();
}

$(document).ready(function() {
    $('#galleryModal').on('show.bs.modal', function (e) {
        $('#galleryImage').attr("src", $(e.relatedTarget).data("large-src"));
    });
})