// 닉네임체크
const nicknameInput = document.getElementById('nickname');
const nicknameError = document.getElementById('nickname-error');

nicknameInput.addEventListener('input', () => {
  const nickname = nicknameInput.value.trim();
  if (!nicknameInput.checkValidity()) {
    nicknameError.textContent =
      '닉네임은 영문자, 숫자, 한글조합 10글자 이하로 작성해주세요.';
    nicknameError.classList.add('error');
  } else {
    nicknameError.textContent = '';
    nicknameError.classList.remove('error');
  }
});
// 비밀번호
$(document).ready(function () {
  const $newPassword = $('#newPassword');
  const $newPasswordCheck = $('#newPasswordCheck');
  const $passwordErr = $('.err.password');

  function checkPasswordMatch() {
    if ($newPassword.val() !== $newPasswordCheck.val()) {
      $passwordErr.text('비밀번호가 일치하지 않습니다.');
    } else {
      $passwordErr.text('');
    }
  }
  $newPassword.on('keyup', checkPasswordMatch);
  $newPasswordCheck.on('keyup', checkPasswordMatch);
});

// 비밀번호 버튼
// const myButton = document.getElementById('myButton');
// const myButtonHidden = document.getElementById('myButtonHidden');
// myButton.addEventListener('click', function () {
//   if (myButtonHidden.style.display === 'none') {
//     myButtonHidden.style.display = 'block';
//   } else {
//     myButtonHidden.style.display = 'none';
//   }
// });

function toggleDiv() {
  var myButton = document.getElementById('myButton');
  var myButtonHidden = document.getElementById('myButtonHidden');
  if (myButtonHidden.style.display === 'none') {
    myButtonHidden.style.display = 'block';
    myButton.style.display = 'none';
  } else {
    myButtonHidden.style.display = 'none';
    myButton.style.display = 'block';
  }
}
// 비밀번호 완료
function toggleOut() {
  var myButton = document.getElementById('myButton');
  var myButtonHidden = document.getElementById('myButtonHidden');
  if (myButtonHidden.style.display === 'block') {
    myButtonHidden.style.display = 'none';
    myButton.style.display = 'block';
  } else {
    myButtonHidden.style.display = 'block';
    myButton.style.display = 'none';
  }
}

// 마이페이지 수정버튼
function modify() {
  Swal.fire({
    title: '수정하시겠습니까?',
    text: '작성하신 정보가 저장됩니다',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#333',
    cancelButtonColor: '#ffd88f',
    confirmButtonText: '수정하기',
    cancelButtonText: '취소하기',
  }).then(result => {
    if (result.isConfirmed) {
      location.replace('./mypage_main.html');
    }
  });
}
// 펫정보 수정페이지
function petModify({ dataset }) {
  Swal.fire({
    title: '수정페이지로 이동하시겠습니까?',
    text: '등록한 반려동물정보를 수정하러 이동합니다',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#333',
    cancelButtonColor: '#ffd88f',
    confirmButtonText: '수정하기',
    cancelButtonText: '취소하기',
  }).then(result => {
    if (result.isConfirmed) {
      //      location.replace('./mypage_pet_modify.html');
      location.replace(`/mypage/${dataset.petNum}/edit`);
    }
  });
}
// function petAdd() {
//   Swal.fire({
//     title: '반려동물정보를 추가 하시겠습니까?',
//     text: '신규등록하러 이동합니다',
//     icon: 'warning',
//     showCancelButton: true,
//     confirmButtonColor: '#333',
//     cancelButtonColor: '#ffd88f',
//     confirmButtonText: '등록하기',
//     cancelButtonText: '취소하기',
//   }).then(result => {
//     if (result.isConfirmed) {
//       location.replace('./mypage_pet_modify.html');
//     }
//   });
// }

/* 사진업로드 */
function DropFile(dropAreaId, fileListId) {
  let dropArea = document.getElementById(dropAreaId);
  let fileList = document.getElementById(fileListId);

  function preventDefaults(e) {
    e.preventDefault();
    e.stopPropagation();
  }

  function highlight(e) {
    preventDefaults(e);
    dropArea.classList.add('highlight');
  }

  function unhighlight(e) {
    preventDefaults(e);
    dropArea.classList.remove('highlight');
  }

  function handleDrop(e) {
    unhighlight(e);
    let dt = e.dataTransfer;
    let files = dt.files;

    handleFiles(files);

    const fileList = document.getElementById(fileListId);
    if (fileList) {
      fileList.scrollTo({ top: fileList.scrollHeight });
    }
  }

  function handleFiles(files) {
    files = [...files];
    // files.forEach(uploadFile);
    files.forEach(previewFile);
  }

  function previewFile(file) {
    console.log(file);
    renderFile(file);
  }

  function renderFile(file) {
    let reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onloadend = function () {
      let img = dropArea.getElementsByClassName('preview')[0];
      img.src = reader.result;
      img.style.display = 'block';
    };
  }

  dropArea.addEventListener('dragenter', highlight, false);
  dropArea.addEventListener('dragover', highlight, false);
  dropArea.addEventListener('dragleave', unhighlight, false);
  dropArea.addEventListener('drop', handleDrop, false);

  return {
    handleFiles,
  };
}

const dropFile = new DropFile('dropFile', 'files');

// 반려동물 정보 추가(2개이상) 되면 이미지 슬라이드
$(document).ready(function () {
  const profileAreas = $('.profile-area');
  const profileList = $('.profile-list__areas');
  if (profileAreas.length > 2) {
    // profile-area 태그가 2개 이상일 때
    $('.profile-list__areas').slick({
      // slick 라이브러리를 적용할 부분 선택자
      slidesToShow: 2, // 한 화면에 보여줄 슬라이드 개수
      slidesToScroll: 1, // 슬라이드 이동 시 이동할 슬라이드 개수
      arrows: false,
    });
  } else {
    profileList.css('width', '40%');
    profileAreas.css('width', '70%');
  }
});
