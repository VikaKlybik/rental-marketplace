function showRequest(message){
    document.getElementById('request').value=message;

}

function changeCarStatus(event, car_id){
    let selectedOption = event.target.value;
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({car_id: car_id, status: selectedOption})
    };
    fetch('/admin/updateCarStatus', options)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

document.getElementById('addNewCar').addEventListener('submit', function(event) {
    event.preventDefault();

    let formData = new FormData(this);

    fetch('/admin/addNewCar', {
        method: 'POST',
        body: formData
    })
        .then(function(response) {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error: ' + response.status);
            }
        })
        .then(function(data) {
            location.reload();
        })
        .catch(function(error) {
            alert('There was a problem with the fetch operation: ' + error.message);
        });
});

$('#editCar').on('show.bs.modal', function (event) {
    const carId = event.relatedTarget.dataset.carId;
    console.log(carId);
    const form = document.querySelector('#editCarForm');
    form.setAttribute('action', '/admin/update/car/'+carId);

    fetch('/admin/get-car/'+carId)
        .then(response => response.json())
        .then(data => {
            document.getElementById('edit_price').value = data.price_per_day.toFixed(2);
            document.getElementById('edit_mileage').value = data.mileage;
            document.getElementById('edit_description').value = data.description;
        })
        .catch(error => console.error(error));

    $('#editCarForm').submit(function(event) {
        // Отменяем стандартное поведение формы
        event.preventDefault();

        // Отправляем данные формы на сервер
        $.ajax({
            type: 'POST',
            url: '/admin/update/car/'+carId,
            data: $(this).serialize(),
            success: function(response) {
                // Если операция выполнена успешно, показываем сообщение об успешном добавлении
                location.reload();
            },
            error: function(xhr, status, error) {
                // Если произошла ошибка, выводим сообщение об ошибке
                alert('There was a problem with the fetch operation:'+ error);
            }
        });
    });
});

$('#addBrandForm').submit(function(event) {
    // Отменяем стандартное поведение формы
    event.preventDefault();

    // Отправляем данные формы на сервер
    $.ajax({
        type: 'POST',
        url: '/admin/addNewBrand',
        data: $(this).serialize(),
        success: function(response) {
            // Если операция выполнена успешно, показываем сообщение об успешном добавлении
            // Закрываем модальное окно
            $('#addBrandModal').modal('hide');
            $('#brandsTable').append(`<tr><td>${response.id}</td><td>${response.brand_name}</td></tr>`);
        },
        error: function(xhr, status, error) {
            // Если произошла ошибка, выводим сообщение об ошибке
            alert('There was a problem with the fetch operation: '+ error);
        }
    });
});


function changeRentalStatus(event, rental_id){
    let selectedOption = event.target.value;
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({id: rental_id, status: selectedOption})
    };
    fetch('/admin/updateRentalStatus', options)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

jQuery(document).ready(function($) {

    "use strict";

    [].slice.call( document.querySelectorAll( 'select.cs-select' ) ).forEach( function(el) {
        new SelectFx(el);
    });

    jQuery('.selectpicker').selectpicker;




    $('.search-trigger').on('click', function(event) {
        event.preventDefault();
        event.stopPropagation();
        $('.search-trigger').parent('.header-left').addClass('open');
    });

    $('.search-close').on('click', function(event) {
        event.preventDefault();
        event.stopPropagation();
        $('.search-trigger').parent('.header-left').removeClass('open');
    });

    $('.equal-height').matchHeight({
        property: 'max-height'
    });

    // var chartsheight = $('.flotRealtime2').height();
    // $('.traffic-chart').css('height', chartsheight-122);


    // Counter Number
    $('.count').each(function () {
        $(this).prop('Counter',0).animate({
            Counter: $(this).text()
        }, {
            duration: 3000,
            easing: 'swing',
            step: function (now) {
                $(this).text(Math.ceil(now));
            }
        });
    });




    // Menu Trigger
    $('#menuToggle').on('click', function(event) {
        var windowWidth = $(window).width();
        if (windowWidth<1010) {
            $('body').removeClass('open');
            if (windowWidth<760){
                $('#left-panel').slideToggle();
            } else {
                $('#left-panel').toggleClass('open-menu');
            }
        } else {
            $('body').toggleClass('open');
            $('#left-panel').removeClass('open-menu');
        }

    });

    $(".menu-item-has-children.dropdown").each(function() {
        $(this).on('click', function() {
            var $temp_text = $(this).children('.dropdown-toggle').html();
            $(this).children('.sub-menu').prepend('<li class="subtitle">' + $temp_text + '</li>');
        });
    });

    // Load Resize
    $(window).on("load resize", function(event) {
        var windowWidth = $(window).width();
        if (windowWidth<1010) {
            $('body').addClass('small-device');
        } else {
            $('body').removeClass('small-device');
        }

    });
});