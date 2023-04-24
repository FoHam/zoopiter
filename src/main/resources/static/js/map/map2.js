//map2.js
let hospitalsFromDB = [];
let hospitalInfoAllFromDB = [];
let dataLoadedCount = 0;
let currentLocation;
if (!currentLocation) {
    // 기본값으로 서울시청 좌표를 사용합니다.
    currentLocation = new kakao.maps.LatLng(37.56667, 126.97806);
}


let mergedHospitalData = null;

$.get("/api/getHospitals", function (data) {
  hospitalsFromDB = data;
  dataLoadedCount++;

  if (dataLoadedCount === 2) {
    mergedHospitalData = mergeHospitalData(hospitalsFromDB, hospitalInfoAllFromDB);
    searchPlaces();
  }
});


$.get("/api/getHospitalinfo", function (data) {
  hospitalInfoAllFromDB = data;
  dataLoadedCount++;

  if (dataLoadedCount === 2) {
    mergedHospitalData = mergeHospitalData(hospitalsFromDB, hospitalInfoAllFromDB);
    searchPlaces();
  }
});


function getHospitalPLIST(hospitalName) {
    const hospitalInfo = hospitalInfoAllFromDB.find(hospital => hospital.h_NAME === hospitalName);
  
    if (hospitalInfo) {
      return hospitalInfo.h_PLIST.split(',').map(animalType => animalType.trim());
    } else {
      return null;
    }
  }
  
function isHospitalInDB(hospitalName) {
    return hospitalsFromDB.some(function (hospital) {
      return hospital.hdName === hospitalName;
    });
  }
  function getHospitalINFO(hospitalName) {
    for (let i = 0; i < hospitalInfoAllFromDB.length; i++) {
        if (hospitalInfoAllFromDB[i].h_NAME === hospitalName) {
            return hospitalInfoAllFromDB[i].h_INFO;
        }
    }
    return null;
}
function getHospitalTIME(hospitalName) {
    for (let i = 0; i < hospitalInfoAllFromDB.length; i++) {
        if (hospitalInfoAllFromDB[i].h_NAME === hospitalName) {
            return hospitalInfoAllFromDB[i].h_TIME;
        }
    }
    return null;
}
function getHospitalINSTINFO(hospitalName) {
    for (let i = 0; i < hospitalInfoAllFromDB.length; i++) {
        if (hospitalInfoAllFromDB[i].h_NAME === hospitalName) {
            return hospitalInfoAllFromDB[i].h_ADDINFO;
        }
    }
    return null;
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

    // 검색 결과 목록에 추가된 항목 제거
    removeAllChildNods(listEl);

    // 지도 마커 제거
    removeMarker();
    var displayedCount = 0; // 표시된 동물병원의 수를 추적하는 변수를 추가
    var hospitalListIndex = 0; // 목록에 표시되는 동물병원의 인덱스를 추적하는 변수를 추가
    const checkedAnimalTypes = getCheckedAnimalTypes();
    for (var i = 0; i < places.length; i++) {
        if (!isHospitalInDB(places[i].place_name)) {
            continue; // 데이터베이스에 없는 동물병원은 건너뛰기
        }
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = isHospitalInDB(places[i].place_name) ? addMarker(placePosition, hospitalListIndex, places[i].place_name) : null,
            itemEl = getListItem(hospitalListIndex, places[i]); // 검색 결과 항목 Element를 생성합니다
            const h_PLIST = getHospitalPLIST(places[i].place_name);
            const checkedAnimalTypes = getCheckedAnimalTypes();
            
            // 선택한 동물 종류에 따라 결과를 필터링합니다.
            // if (checkedAnimalTypes.length > 0 && !isHospitalHandlingAnimalType(h_PLIST, checkedAnimalTypes)) {
            //     continue;
            // }

            if (checkedAnimalTypes.length > 0) {
                let matchedFilters = 0;
                for (let animalType of checkedAnimalTypes) {
                    if (h_PLIST.includes(animalType)) {
                        matchedFilters++;
                    }
                }
    
                // 모든 필터가 매치되지 않으면 건너뛰기
                if (matchedFilters !== checkedAnimalTypes.length) {
                    continue;
                }
            }
///////////////////////////////////////////////////////////////////////////////////////////
        // h_PLIST
        
        
        // h_NAME 및 h_PLIST 값을 콘솔에 출력합니다.
        // console.log("Hospital Name:", places[i].place_name, "h_PLIST:", h_PLIST);

        // 병원 정보를 표시할 div에 펫 종류 목록 (h_PLIST) 추가
        const petTag = document.createElement('div');
        petTag.className = 'petTag';
        petTag.innerText = h_PLIST;
        if (itemEl.querySelector('.info')) {
            itemEl.querySelector('.info').appendChild(petTag);
        }
///////////////////////////////////////////////////////////////////////////////////////////
        // h_INFO
        const h_INFO = getHospitalINFO(places[i].place_name);
        // 병원 정보를 표시할 div에 추가 정보 (h_INFO) 추가
        const addInfoTag = document.createElement('p');
        addInfoTag.className = 'addInfo';
        addInfoTag.innerText = h_INFO;
        if (itemEl.querySelector('.info__other')) {
            itemEl.querySelector('.info__other').appendChild(addInfoTag);
        }
        // 상세정보 팝업 업데이트
        const addInfoElementDetails = document.querySelector(`.list-details${hospitalListIndex + 1} .addInfo`);
        addInfoElementDetails.innerText = h_INFO;
        // 검색결과 목록의 업데이트합니다.
        const addInfoElement = document.querySelector(`.list-content${hospitalListIndex + 1} .addInfo`);
        addInfoElement.innerText = h_INFO;
///////////////////////////////////////////////////////////////////////////////////////////
        // h_TIME
        const h_TIME = getHospitalTIME(places[i].place_name);

        // 상세정보 팝업 업데이트
        const infoTimeElementDetails = document.querySelector(`.list-details${hospitalListIndex + 1} .infoTime`);
        infoTimeElementDetails.innerText = h_TIME;
///////////////////////////////////////////////////////////////////////////////////////////
        // h_ADDINFO
        const h_ADDINFO = getHospitalINSTINFO(places[i].place_name);

        // 상세정보 팝업 업데이트
        const instInfoTimeElementDetails = document.querySelector(`.list-details${hospitalListIndex + 1} .instInfo`);
        instInfoTimeElementDetails.innerText = h_ADDINFO;

///////////////////////////////////////////////////////////////////////////////////////////
        fragment.appendChild(itemEl);

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
        // 펫 종류 목록 (h_PLIST)을 업데이트합니다.
        const petTagElement = document.querySelector(`.list-content${hospitalListIndex + 1} .petTag`);
        petTagElement.innerText = h_PLIST;

        // 병원 상세정보 팝업의 펫 종류 목록 (h_PLIST)을 업데이트합니다.
        const petTagElementDetails = document.querySelector(`.list-details${hospitalListIndex + 1} .petTag`);
        petTagElementDetails.innerText = h_PLIST;
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

        displayedCount++; // 표시된 동물병원의 수를 증가

        if (displayedCount >= 4) { // 최대 4개의 동물병원이 표시되면 루프를 종료
            break;
        }
        hospitalListIndex++; // 목록에 표시되는 동물병원의 인덱스를 증가
    }

    // 검색결과 항목들을 검색결과 목록 Element에 추가
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정
    map.setBounds(bounds);
}




// 검색결과 항목을 반환하는 함수
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


// 마커를 생성하고 지도 위에 마커를 표시하는 함수
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
    markers.push(marker); // 배열마커 추가

    return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거
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
            if (!currentLocation) {
                // 기본값으로 서울시청 좌표를 사용합니다.
                currentLocation = new kakao.maps.LatLng(37.56667, 126.97806);
            }
            searchPlaces();
        });
    } else {
        console.error("Geolocation is not supported by this browser.");
        if (!currentLocation) {
            // 기본값으로 서울시청 좌표를 사용합니다.
            currentLocation = new kakao.maps.LatLng(37.56667, 126.97806);
        }
        searchPlaces();
    }
    document.getElementById("myPlace").addEventListener("click", function() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(pos) {
                success(pos); // 위치 정보를 가져온 후 지도 중심을 현재 위치로 이동
                if (!currentLocation) {
                    // 기본값으로 서울시청 좌표를 사용합니다.
                    currentLocation = new kakao.maps.LatLng(37.56667, 126.97806);
                }
                searchPlaces();
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
    map.setCenter(currentLocation); // 지도의 중심을 현재 위치로 이동
}

