//map2.js
let hospitalsFromDB = [];

$.get("/api/getHospitals", function (data) {
  hospitalsFromDB = data;

  searchPlaces();
});
///////////////////////////////////////////////////
$.get("/api/getHospitalinfo", function (data) {
    hospitalInfoAllFromDB = data;

  searchPlaces();
});

function isHospitalInDB(hospitalName) {
    return hospitalsFromDB.some(function (hospital) {
      return hospital.hdName === hospitalName;
    });
  }
  

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {
    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();

    var listEl = document.getElementById('placesList'),
        menuEl = document.getElementById('menu_wrap'),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds(),
        listStr = '';

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();
    var displayedCount = 0; // 표시된 동물병원의 수를 추적하는 변수를 추가
    var hospitalListIndex = 0; // 목록에 표시되는 동물병원의 인덱스를 추적하는 변수를 추가

    for (var i = 0; i < places.length; i++) {
        if (!isHospitalInDB(places[i].place_name)) {
            continue; // 데이터베이스에 없는 동물병원은 건너뛰기
        }
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = isHospitalInDB(places[i].place_name) ? addMarker(placePosition, hospitalListIndex, places[i].place_name) : null,
            itemEl = getListItem(hospitalListIndex, places[i]); // 검색 결과 항목 Element를 생성합니다
        
        const nameElement = document.querySelector(`.list-content${hospitalListIndex + 1} .hospital-name`);
        const addressElement = document.querySelector(`.list-content${hospitalListIndex + 1} .hospital-address`);
        updateAddressInfo(places[i], nameElement, addressElement);

        // 거리 계산
        const distance = calculateDistance(
            currentLocation.getLat(), currentLocation.getLng(),
            places[i].y, places[i].x
        );
        // list-content 클래스를 사용하여 거리 정보를 업데이트할 span 요소를 선택합니다.
        const distanceElement = document.querySelector(`.list-content${hospitalListIndex + 1} #regionDis`);
        // 거리 정보를 업데이트합니다.
        distanceElement.innerHTML = distance.toFixed(1) + 'km';

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        if (marker) { // marker가 null이 아닌 경우에만 이벤트를 추가합니다.
            (function (marker, title) {
                kakao.maps.event.addListener(marker, 'mouseover', function () {
                    displayInfowindow(marker, title, distance);
                });

                kakao.maps.event.addListener(marker, 'mouseout', function () {
                    infowindow.close();
                });

                itemEl.onmouseover = function () {
                    displayInfowindow(marker, title, distance);
                };

                itemEl.onmouseout = function () {
                    infowindow.close();
                };
            })(marker, places[i].place_name);
            
        }
        

        
        fragment.appendChild(itemEl);

        displayedCount++; // 표시된 동물병원의 수를 증가시킵니다.

        if (displayedCount >= 4) { // 최대 4개의 동물병원이 표시되면 루프를 종료합니다.
            break;
        }
        hospitalListIndex++; // 목록에 표시되는 동물병원의 인덱스를 증가시킵니다.
    }

    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
}

// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, places) {
    var el = document.createElement('li');
    el.className = 'item';
    el.innerHTML = `
        <span class="markerbg marker_${index + 1}"></span>
        <div class="info">
            <h5>${places.place_name}</h5>
            <span>${places.road_address_name ? places.road_address_name : places.address_name}</span>
            <span class="jibun gray">${places.address_name}</span>
            <span class="tel">${places.phone}</span>
        </div>
    `;
    return el;
}


// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png',
        imageSize = new kakao.maps.Size(36, 37),
        imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691),
            spriteOrigin: new kakao.maps.Point(0, (idx*46)+10),
            offset: new kakao.maps.Point(13, 37)
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position,
            image: markerImage
        });

    marker.setMap(map);
    markers.push(marker); // 배열에 마커 추가

    return marker;
}




// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for ( var i = 0; i < markers.length; i++ ) {
        markers[i].setMap(null);
    }   
    markers = [];
}

window.onload = function () {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(success, function(error) {
            console.error("Error: ", error);
            searchPlaces(); // 위치 정보를 가져오지 못한 경우 기본 검색을 수행합니다.
        });
    } else {
        console.error("Geolocation is not supported by this browser.");
        searchPlaces(); // 위치 정보를 지원하지 않는 브라우저에서 기본 검색을 수행합니다.
    }
    document.getElementById("myPlace").addEventListener("click", function() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(pos) {
                success(pos); // 위치 정보를 가져온 후 지도 중심을 현재 위치로 이동합니다.
                searchPlaces(); // 병원 검색을 수행합니다.
            }, function(error) {
                console.error("Error: ", error);
            });
        } else {
            console.error("Geolocation is not supported by this browser.");
        }
    });
    
};


function success(pos) {
    var crd = pos.coords;
    currentLocation = new kakao.maps.LatLng(crd.latitude, crd.longitude);
    map.setCenter(currentLocation); // 지도의 중심을 현재 위치로 이동합니다.

    // searchPlaces(currentLocation); // 현재 위치를 매개변수로 전달하여 함수를 호출합니다.
}

