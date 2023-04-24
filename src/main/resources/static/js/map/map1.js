//map1.js
// 마커를 담을 배열입니다
var markers = [];

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = {
        center: new kakao.maps.LatLng(35.5383773, 129.3113596), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };  

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();  

// 검색 결과 목록이나 마커를 마우스오버했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({
    zIndex:1,
    content: '<span class="info-title"></span>'
});


// 장소검색이 완료됐을 때 호출되는 콜백함수
function placesSearchCB(data, status, pagination) {
  if (status === kakao.maps.services.Status.OK) {
      // 여기서 필터링을 처리하도록 변경
      var filteredPlaces = filterPlacesByAnimalType(data);
      displayPlaces(filteredPlaces);
      displayPagination(pagination);
  } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
      alert("검색 결과가 없습니다.");
      return;
  } else {
      alert("검색 결과를 가져올 수 없습니다.");
      return;
  }
}




function updateAddressInfo(place, nameElement, addressElement) {
    const name = place.place_name;
    const roadAddress = place.road_address_name;
    const jibunAddress = place.address_name;

    nameElement.innerHTML = name;
    addressElement.innerHTML = `${roadAddress} (${jibunAddress})`;
}

function calculateDistance(lat1, lon1, lat2, lon2) {
    function deg2rad(deg) {
        return deg * (Math.PI / 180);
    }

    var R = 6371; // 지구의 반지름
    var dLat = deg2rad(lat2 - lat1);
    var dLon = deg2rad(lon2 - lon1);
    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var distance = R * c;

    // 결과를 소수점 한 자리까지 반올림
    return Math.round(distance * 10) / 10;
}

function toRad(value) {
    return (value * Math.PI) / 180;
}

document.addEventListener('DOMContentLoaded', function () {
    const infoButtons = document.querySelectorAll('.list-content__btn');
    const closeButtons = document.querySelectorAll('.list-details__header i');
  
    infoButtons.forEach((infoButton, index) => {
      infoButton.addEventListener('click', function () {
        const listDetails = document.getElementById(`list-details${index + 1}`);
        const listContent = this.parentElement;
  
        // 병원 이름, 주소 및 거리 값
        const hospitalName = listContent.querySelector('.hospital-name').textContent;
        const hospitalAddress = listContent.querySelector('.hospital-address').textContent;
        const hospitalDistance = listContent.querySelector('.hospital-address + span').textContent;
  
        // 다이얼로그에 값을 설정
        listDetails.querySelector(`#hospitalName${index + 1}`).textContent = hospitalName;
        listDetails.querySelector(`#region${index + 1}`).textContent = hospitalAddress +("\u00a0") +' / ' +("\u00a0");
        listDetails.querySelector(`#regionDis${index + 1}`).textContent = hospitalDistance;
  
        listDetails.showModal();
      });
    });
  
    closeButtons.forEach((closeButton) => {
      closeButton.addEventListener('click', function () {
        this.parentElement.parentElement.close();
      });
    });
  });
  
  function filterPlacesByAnimalType(places) {
    const checkedAnimalTypes = getCheckedAnimalTypes();

    if (checkedAnimalTypes.length === 0) {
        return places;
    }

    return places.filter(place => {
        const h_PLIST = getHospitalPLIST(place.place_name);
        return isHospitalHandlingAnimalType(h_PLIST, checkedAnimalTypes);
    });
}


  function getCheckedAnimalTypes() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    let checkedAnimalTypes = [];
    for (const checkbox of checkboxes) {
        if (checkbox.checked) {
            checkedAnimalTypes.push(checkbox.name);
        }
    }
    return checkedAnimalTypes;
}

  
function isHospitalHandlingAnimalType(h_PLIST, checkedAnimalTypes) {
  if (h_PLIST === null) {
      return false;
  }

  for (const animalType of checkedAnimalTypes) {
      if (h_PLIST.includes(animalType)) {
          return true;
      }
  }
  return false;
}





function mergeHospitalData(hospitals, hospitalInfo) {
  const mergedData = {};

  hospitals.forEach(hospital => {
    mergedData[hospital.name] = {
      animalTypes: []
    };
  });

  hospitalInfo.forEach(info => {
    const hospitalName = info.hospital_name;
    const animalType = info.animal_type;

    if (mergedData.hasOwnProperty(hospitalName)) {
      mergedData[hospitalName].animalTypes.push(animalType);
    }
  });

  return mergedData;
}


function getCheckedAnimalTypes() {
  const dogCheckbox = document.getElementById("dogCheckbox");
  const catCheckbox = document.getElementById("catCheckbox");
  const smallCheckbox = document.getElementById("smallCheckbox");
  const otherCheckbox = document.getElementById("otherCheckbox");

  const checkedAnimalTypes = [];

  if (dogCheckbox.checked) checkedAnimalTypes.push("강아지");
  if (catCheckbox.checked) checkedAnimalTypes.push("고양이");
  if (smallCheckbox.checked) checkedAnimalTypes.push("소동물");
  if (otherCheckbox.checked) checkedAnimalTypes.push("기타");

  return checkedAnimalTypes;
}

function isHospitalHandlingAnimalType(h_PLIST, checkedAnimalTypes) {
  if (h_PLIST === null || h_PLIST.length === 0) {
      return false;
  }

  for (const animalType of checkedAnimalTypes) {
      if (h_PLIST.includes(animalType)) {
          return true;
      }
  }
  return false;
}


document.getElementById("petTagBtn").addEventListener("click", function (e) {
  e.preventDefault();
  searchPlaces();
});
