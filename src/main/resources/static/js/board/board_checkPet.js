//import { ajax } from '/js/ajax.js';


// 동물 태그 체크박스 이벤트

const $selectBox = document.querySelector('.left .selectBox');
const $check = document.querySelectorAll('input[type="checkbox"]');

let addBtn;
let values = [];

const checkbox_h = e => {
    let checkedCnt = 0;

    // 동물태그 체크박스 선택했을 시 전체 선택 해제 버튼 추가
    for (const ele of $check) {
        if (ele.checked == true) {
            checkedCnt++;
        } else {
            checkedCnt - 1;
        }
    }

    if (checkedCnt === 1) {
        addBtn = document.createElement("button");
        addBtn.innerHTML = '전체 선택 해제';
        addBtn.setAttribute("id", "allBtn");
        $selectBox.appendChild(addBtn);
    } else if (checkedCnt === 0 || checkedCnt === $check.length + 1) {
        $selectBox.removeChild(addBtn);
    }
    // 전체 체크 해제 버튼
    const $allBtn = document.getElementById("allBtn");
    $allBtn.addEventListener('click', e => {
        e.preventDefault();
        $check.forEach((checkedEle) => {
            checkedEle.checked = false;
        });
        if (checkedCnt === 0) {
            $selectBox.removeChild(addBtn);
        }
    }, false);
};

//const sendValues_h = e => {
//    e.preventDefault();
//    // unchecked 된 값들을 담을 배열
//    const unckValue = [];
//        // values 배열에 체크한 value값 담기
//        for (const ele of $check) {
//            if (ele.checked == true) {
//                values.push(ele.value);
//            }else{
//               unckValue.push(ele.value);
//            }
//        }
//        // unchecked 된 값 기존 배열에서 제거
//        const result = values.filter((item) => !unckValue.includes(item));
//        // 체크된 값을 담은 배열 확인
//        console.log(result);
//
//       const url = `/api/bbsc/petType?petType=${result[0]}`;
//       ajax
//          .get(url)
//          .then(res => res.json())
//          .then(changeList)
//          .catch(console.error);
//       return;
//
//}

$selectBox.addEventListener('change', checkbox_h, false);
//checkBtn.addEventListener('click',sendValues_h,false);

