// 게시글 아이디
//const bbscId = document.getElementById("bbscId");
//// 게시글 영역
//const $posts = document.querySelectorAll(".post");
//// 좋아요 버튼
//const $likeBtns = document.querySelectorAll('.like-icons .fa-heart');
//
//const  stopFunc = function(e) { e.preventDefault(); e.stopPropagation(); return false; };
//
//    for(const ele of $posts){
//        for(const ele2 of $likeBtns){
//          // 좋아요 클릭이벤트 발생하면 글 클릭 이벤트 막기
//          if(ele2.hasAttribute('onclick')){
//            ele.addEventListener('click',stopFunc,true);
//          }else{
//           ele.addEventListener('click', e => {
//              const url = `/bbsc/${bbscId.value}/detail`;
//              location.href = url;
//            });
//          }
//          // 좋아요 버튼 클릭이벤트있는지 확인
//         console.log(ele2.hasAttribute('onclick'));
//      }
//
//    }

// 동물태그 적용버튼
const $checkBtn = document.getElementById('checkBtn');
// 동물 태그
const $petTags = document.querySelectorAll('.petTag');
// 조회방법 셀렉트박스
const $searchType = document.getElementById('searchType');

// 선택한 동물태그 값
 const result = [];
 for(const ele of $petTags){
     if(ele.checked){
        result.push(ele.value);
     }
        console.log(result);
 }


// 조회방법 선택 시
const search_f = e => {
    const url = `/bbsc/list/1/${$searchType.value}?category=${result}`;
    location.href = url;
}

$checkBtn.addEventListener('click', search_f, false);
$searchType.addEventListener('change', search_f, false);

