// 수정 화면으로 가기
const $modifyBtn = document.getElementById('modifyBtn');

$modifyBtn?.addEventListener('click', e => {
   const url = `/bbsc/${bbscId.value}/edit`;
   location.href = url;
});

// 삭제
const $delBtn = document.getElementById('delBtn');
$delBtn?.addEventListener('click', e => {
  if(confirm('삭제하시겠습니까?')){
    const url = `/bbsc/${bbscId.value}/del`;
    location.href = url;
  }
});

