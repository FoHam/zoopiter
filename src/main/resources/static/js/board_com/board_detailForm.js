import { ajax } from '/js/ajax.js';

const $bbscId = document.getElementById('bbscId');
const $userNick = document.getElementById('userNick');
// 댓글 내용
const $ccContent = document.getElementById('ccContent');
// 댓글 아이디
const $ccId = document.getElementById('ccId');
// 댓글 수정 버튼
const $updateBtn1 = document.getElementById('replyUpdateBtn1');
// 댓글 수정 확인 버튼
const $updateBtn2 = document.getElementById('replyUpdateBtn2');


// 수정 화면으로 가기
const $modifyBtn = document.getElementById('modifyBtn');

$modifyBtn?.addEventListener('click', e => {
   const url = `/bbsc/${$bbscId.value}/edit`;
   location.href = url;
});

// 삭제
const $delBtn = document.getElementById('delBtn');
$delBtn?.addEventListener('click', e => {
  if(confirm('삭제하시겠습니까?')){
    const url = `/bbsc/${$bbscId.value}/del`;
    location.href = url;
  }
});


//댓글 등록버튼
const $addBtn = document.getElementById('addBtn');
//댓글 취소버튼
const $cancelBtn = document.getElementById('cancelBtn');

// 페이지 새로고침
function resetPage(){
    const url = `/bbsc/${$bbscId.value}/detail`;
    location.href= url;
}

//댓글 등록
const add_h = e => {
   $ccContent.disabled == false;
   const url = '/api/bbscReply/save';
   const payLoad = {
    "bbscId": $bbscId.value,
    "ccContent":$ccContent.value,
    "userNick": $userNick.textContent
   };
    ajax
       .post(url, payLoad)
       .then(res => res.json())
       .then(resetPage)
       .catch(console.error); //err=>console.error(err)
       return;

}

$addBtn.addEventListener('click',add_h,false);

//댓글 등록취소
$cancelBtn.addEventListener('click',e => {
   $ccContent.disabled == true;
    $ccContent.value = '';
},false);

//댓글 삭제버튼
const $replyDelBtn = document.getElementById('replyDelBtn');

//댓글 삭제
const del_h = e => {
    const url = `/api/bbscReply/del/${$ccId.textContent}`;
     if(confirm('삭제하시겠습니까?')){
        ajax
            .delete(url)
            .then(res => res.json)
            .then(resetPage)
            .error(console.error);
     }
}
$replyDelBtn?.addEventListener('click', del_h, false);

// 수정 삭제 버튼영역
const commentBtns = document.querySelector('.comment__btn');

// 확인, 취소 버튼영역
const updateBtns = document.querySelector('.updateArea');

// 댓글 본문 영역
const $originComment = document.getElementById('originComment');


// 댓글 수정사항 확인, 취소버튼 보이게, 댓글 입력 영역 활성화
const updateSet_h = e => {
    commentBtns.style.display = 'none';
    updateBtns.style.display = 'flex';
    $originComment.readOnly = false;
    $originComment.focus();
}

// 댓글 수정버튼 이벤트
$updateBtn1?.addEventListener('click', updateSet_h, false);

// 수정 취소 버튼
const $replyCancleBtn = document.getElementById('replyCancleBtn');
$replyCancleBtn?.addEventListener('click', e => {
    commentBtns.style.display = 'flex';
    updateBtns.style.display = 'none';
    $originComment.readOnly = true;
});

// 댓글 수정 이벤트
const update_h = e => {
   const url = `/api/bbscReply/update/${$bbscId.value}`;
    const payLoad = {
        "ccId":$ccId.textContent,
        "ccContent":$ccContent.value
     };

    ajax
        .patch(url,payLoad)
        .then(res => res.json())
        .then(resetPage)
        .catch(console.error);
    return;
}

// 댓글 확인버튼 이벤트
$updateBtn2?.addEventListener('click', update_h, false);

//댓글란이 읽기 전용이면 포커스 아웃라인 안보이게
if($originComment?.readOnly == true){
   $originComment.style.outline = 'none';
}

// 좋아요 버튼
// 클릭 전 아이콘
const $prevHeart = document.querySelectorAll('.like-icons .fa-regular');

const increaseLike_h = e => {
        console.log('increaseLike!');
        const url = `/api/bbsc/${$bbscId.value}/like`;
        ajax
            .get(url)
            .then(res => res.json())
            .catch(console.error);
        return;
};

const decreaseLike_h = e => {
    console.log('deleteLike!');
    const url = `/api/bbsc/${$bbscId.value}/like`;
    ajax
            .delete(url)
            .then(res => res.json())
            .catch(console.error);
        return;
};

const heart_h = e => {
    e.stopPropagation();
    const target = e.target;
    if (target.className == "fa-regular fa-heart") {
        target.onclick = increaseLike_h;
        target.classList.remove("fa-regular", "fa-heart");
        target.classList.add("fa-solid", "fa-heart");
    } else if (target.className == "fa-solid fa-heart") {
        target.onclick = decreaseLike_h;
        target.classList.remove("fa-solid", "fa-heart");
        target.classList.add("fa-regular", "fa-heart");
    }
};

for (const ele of $prevHeart) {
    ele.addEventListener('click', heart_h, false);
}
