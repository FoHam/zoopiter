import { ajax } from '/js/ajax.js';

const $btnWithdraw = document.getElementById('btnWithdraw');

// 탈퇴
const withdraw_h = () => {
  const url = `/api/mypage/withdraw`;
  ajax
    .get(url)
    .catch(console.error); //err=>console.error(err)
  return;
};

const withdraw2_h = () => {
  const url = `/api/mypage/withdraw2`;
  ajax
    .get(url)
    .catch(console.error); //err=>console.error(err)
  return;
};

function withdraw() {
  const userId = document.getElementById('btnWithdraw').value;
  Swal.fire({
    title: '탈퇴하시겠습니까?',
    text: '탈퇴확인시 등록된 정보 및 게시글이 전체 삭제 됩니다',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#333',
    cancelButtonColor: '#ffd88f',
    confirmButtonText: '탈퇴하기',
    cancelButtonText: '취소하기',
  }).then(result => {
    if (result.isConfirmed) {
        withdraw_h();
        withdraw2_h();
        Swal.fire('탈퇴완료!', '그동안 이용해주셔서 감사합니다', 'success').then(result=>{
              if (result.isConfirmed){
                location.href = '/';
              };
      });
    };
  });
};

//탈퇴버튼 클릭
$btnWithdraw.addEventListener('click',withdraw);


// $(function () {
//   $('#btnModify').click(function () {
//     var userId = $(this).data('userId');
//   });
// });
