// ymaps.ready(init);
//
// function init() {
//     var myPlacemark,
//         myMap = new ymaps.Map('map', {
//             center: [55.753994, 37.622093],
//             zoom: 9
//         }, {
//             searchControlProvider: 'yandex#search'
//         });
//
//     // Слушаем клик на карте.
//     myMap.events.add('click', function (e) {
//         var coords = e.get('coords');
//
//         // Если метка уже создана – просто передвигаем ее.
//         if (myPlacemark) {
//             myPlacemark.geometry.setCoordinates(coords);
//         }
//         // Если нет – создаем.
//         else {
//             myPlacemark = createPlacemark(coords);
//             myMap.geoObjects.add(myPlacemark);
//             // Слушаем событие окончания перетаскивания на метке.
//             myPlacemark.events.add('dragend', function () {
//                 getAddress(myPlacemark.geometry.getCoordinates());
//             });
//         }
//         getAddress(coords);
//     });
//
//     // Создание метки.
//     function createPlacemark(coords) {
//         return new ymaps.Placemark(coords, {
//             iconCaption: 'поиск...'
//         }, {
//             preset: 'islands#violetDotIconWithCaption',
//             draggable: true
//         });
//     }
//
//     // Определяем адрес по координатам (обратное геокодирование).
//     function getAddress(coords) {
//         myPlacemark.properties.set('iconCaption', 'поиск...');
//         ymaps.geocode(coords).then(function (res) {
//             var firstGeoObject = res.geoObjects.get(0);
//
//             myPlacemark.properties
//                 .set({
//                     // Формируем строку с данными об объекте.
//                     iconCaption: [
//                         // Название населенного пункта или вышестоящее административно-территориальное образование.
//                         firstGeoObject.getLocalities().length ? firstGeoObject.getLocalities() : firstGeoObject.getAdministrativeAreas(),
//                         // Получаем путь до топонима, если метод вернул null, запрашиваем наименование здания.
//                         firstGeoObject.getThoroughfare() || firstGeoObject.getPremise()
//                     ].filter(Boolean).join(', '),
//                     // В качестве контента балуна задаем строку с адресом объекта.
//                     balloonContent: firstGeoObject.getAddressLine()
//                 });
//         });
//     }
// }
ymaps.ready(function () {
    ymaps.geolocation.get().then(function (result) {
        var location = result.geoObjects.get(0).geometry.getCoordinates();
        console.log(location);
        var myMap = new ymaps.Map("myMap", {
            center: location,
            zoom: 14
        });

        var myPlacemark;

        myMap.events.add('click', function (e) {
            var coords = e.get('coords');
            ymaps.geocode(coords).then(function (res) {
                var firstGeoObject = res.geoObjects.get(0);
                var address = firstGeoObject.getAddressLine();

                if (myPlacemark) {
                    myPlacemark.geometry.setCoordinates(coords);
                    myPlacemark.properties.set('iconCaption', address);
                } else {
                    myPlacemark = createPlacemark(coords, address);
                    myMap.geoObjects.add(myPlacemark);
                }
            });
        });

        function createPlacemark(coords, address) {
            return new ymaps.Placemark(coords, {
                iconCaption: address,
            }, {
                preset: 'islands#blueDotIconWithCaption',
                draggable: true
            });
        }

        document.getElementById('addressForm').addEventListener('submit', function (event){
            event.preventDefault()
            if(myPlacemark) {
                let coords = myPlacemark.geometry.getCoordinates();
                let address = myPlacemark.properties.get('iconCaption');
                ymaps.geocode(coords).then(function (res) {
                    let firstGeoObject = res.geoObjects.get(0);
                    let country = firstGeoObject.getCountry();
                    let city = firstGeoObject.getLocalities().length ? firstGeoObject.getLocalities() : firstGeoObject.getAdministrativeAreas();
                    let geolocationDataRequest = {
                        latitude: coords[0],
                        longitude: coords[1],
                        address: address,
                        country: country,
                        city: city[0]
                    };

                    // Отправка данных на сервер
                    sendDataToServer(geolocationDataRequest);
                }).catch((error) => {
                    console.error('Error in geocoding:', error);
                });
            }
        });
    }).catch(function (error) {
        // Handle error if geolocation fails
        console.error('Geolocation failed:', error);
    });
});

function sendDataToServer(data) {
    let path = window.location.href
    let bodyObject = JSON.stringify(data)
    // Отправка данных на сервер
    fetch(path, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: bodyObject
    })
        .then(response => response.json())
        .then(data => {
            const redirectUrl = data.url; // Assuming the JSON response contains the redirect URL
            if (redirectUrl) {
                window.location.href = redirectUrl; // Redirect to the new URL
            } else {
                document.querySelector('#message').textContent = "Ошибка обновления данных, попробуйте ещё раз!";
                clearForm();
                console.log(document.querySelector('#message'))
                document.querySelector('#overlay-message').style.display = 'block';
                document.querySelector('#modal-message').style.display = 'block';
            }
        })
        .catch(error => {
            document.querySelector('#message').textContent = "Ошибка обновления данных, попробуйте ещё раз!";
            clearForm();
            console.log(document.querySelector('#message'))
            document.querySelector('#overlay-message').style.display = 'block';
            document.querySelector('#modal-message').style.display = 'block';
        });
}