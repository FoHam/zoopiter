import { ajax } from '/js/ajax.js';
// 닉네임체크
const nicknameInput = document.getElementById('nickname');
const nicknameError = document.getElementById('nickname-error');

//<!--사이드메뉴 강조-->
      $(function () {
        $('#mypagePcGnb').children().eq(0).find('a').addClass('on');
      });

//닉네임 중복체크
const chkNick = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      nicknameError.style = 'color : red';
      nicknameError.textContent = '사용중인 닉네임 입니다.';
    } else {
      nicknameError.style = 'color : green';
      nicknameError.textContent = '사용가능한 닉네임 입니다.';
    }
  } else {
    nicknameError.textContent = `${res.header.rtmsg}`;
  };
  return;
};

const chkNick_h = () => {
  const url = `/api/members/nickname?nickname=${nicknameInput.value}`;
  ajax
    .get(url)
    .then(res => res.json())
    .then(chkNick) //res=>chkEmail(res)
    .catch(console.error); //err=>console.error(err)
  return;
};

// 닉네임
nicknameInput.addEventListener('keydown', e => {
  const input = nicknameInput.value;
  const lenOfInput = input.length;

  if (e.key === ' ') {
    e.preventDefault();
  }

  if (e.key != 'Enter') {
    return;
  }
  if (e.key == 'Enter') {
    if (/[^A-Za-z0-9ㄱ-힣]/.test(input)) {
      nicknameError.classList.remove('hidden');
      nicknameError.style = 'color : red';
      nicknameError.textContent = '* 영문 숫자 한글만 입력 가능합니다.';
      nicknameInput.focus();
      return;
    }
    if (lenOfInput == 0) {
      nicknameError.classList.remove('hidden');
      nicknameError.style = 'color : red';
      nicknameError.textContent = '* 닉네임를 입력해 주세요.';
      nicknameInput.focus();
      nicknameInput.value = '';
    } else {
      //      $errNickname.classList.add('hidden');
      chkNick_h();
      // $nickname.value = input;
    }
    return;
  }
  return;
});

nicknameInput.addEventListener('blur', e => {
  const input = nicknameInput.value;
  const lenOfInput = input.length;

  if (/[^A-Za-z0-9ㄱ-힣]/.test(input)) {
    nicknameError.classList.remove('hidden');
    nicknameError.style = 'color : red';
    nicknameError.textContent = '* 영문 숫자 한글만 입력 가능합니다.';
    return;
  }
  if (lenOfInput == 0) {
    nicknameError.classList.remove('hidden');
    nicknameError.style = 'color : red';
    nicknameError.textContent = '* 닉네임을 입력해 주세요.';
    nicknameInput.value = '';
  } else {
    //    $errNickname.classList.add('hidden');
    chkNick_h();
    // $nickname.value = input;
  }
  return;
});

// 은아님 원래 했던 닉네임 체크
//nicknameInput.addEventListener('input', () => {
//  const nickname = nicknameInput.value.trim();
//  if (!nicknameInput.checkValidity()) {
//    nicknameError.textContent =
//      '닉네임은 영문자, 숫자, 한글조합 10글자 이하로 작성해주세요.';
//    nicknameError.classList.add('error');
//  } else {
//    nicknameError.textContent = '';
//    nicknameError.classList.remove('error');
//  }
//});


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
const myButton = document.getElementById('myButton');
const myButtonCom = document.getElementById('myButtonCom');
const myButtonHidden = document.getElementById('myButtonHidden');
const myPwChk = document.getElementById('myPwChk');

const password = document.getElementById('password');
const $pw = document.getElementById('newPassword');
const $pwCheck = document.getElementById('newPasswordCheck');
const passwordError = document.getElementById('password-error');
const $errPw = document.getElementById('newPassword-error');
const $errPwCheck = document.getElementById('newPasswordCheck-error');



//기존 비밀번호 맞는지 확인
const isExistPw = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      passwordError.style = 'color : green';
      passwordError.textContent = '비밀번호가 맞습니다';
    } else {
      passwordError.style = 'color : red';
      passwordError.textContent = '비밀번호가 틀립니다';
    }
  } else {
    $errNickname.textContent = `${res.header.rtmsg}`;
  };
  return;
};

const isExistPw_h = () => {
  const url = `/api/mypage/pw?pw=${password.value}`;
  ajax
    .get(url)
    .then(res => res.json())
    .then(isExistPw) //res=>chkEmail(res)
    .catch(console.error); //err=>console.error(err)
  return;
};

myPwChk.addEventListener('click',isExistPw_h);

//비밀번호 확인 함수
const pwCheck_h = e => {
  const input = $pwCheck.value;
  const inputChk = $pw.value;
  const lenOfInput = input.length;

  if (lenOfInput != 0) {
    if (input == inputChk) {
      $errPw.classList.add('hidden');
      $errPwCheck.style = 'color : green';
      $errPwCheck.textContent = '비밀번호가 일치합니다';
    } else {
      $errPwCheck.classList.remove('hidden');
      $errPwCheck.style = 'color : red';
      $errPwCheck.textContent = '비밀번호가 일치하지 않습니다.';
    }
  }
  return;
};

//비밀번호
$pw.addEventListener('keydown', e => {
  const input = $pw.value;
  const lenOfInput = input.length;

  if (e.key === ' ') {
    e.preventDefault();
  }

  if (e.key != 'Enter') {
    return;
  }
  if (e.key == 'Enter') {
    if (/[^A-Za-z0-9]/.test(input)) {
      $errPw.classList.remove('hidden');
      $errPw.style = 'color : red';
      $errPw.textContent = '* 영문 대소문자 숫자만 입력 가능합니다.';
      $pw.focus();
      return;
    }
    if (lenOfInput == 0) {
      $errPw.classList.remove('hidden');
      $errPw.style = 'color : red';
      $errPw.textContent = '* 비밀번호를 입력해 주세요.';
      $pw.focus();
    } else if (8 > lenOfInput || lenOfInput > 20) {
      $errPw.classList.remove('hidden');
      $errPw.style = 'color : red';
      $errPw.textContent = '* 비밀번호는 8~20자 입력 가능합니다.';
      $pw.focus();
    } else {
      $errPw.classList.add('hidden');
      $errPw.textContent = '';
      $pwCheck.focus();
    }
    return;
  }
  return;
});

$pw.addEventListener('blur', e => {
  const input = $pw.value;
  const lenOfInput = input.length;

  if (/[^A-Za-z0-9]/.test(input)) {
    $errPw.classList.remove('hidden');
    $errPw.style = 'color : red';
    $errPw.textContent = '* 영문 대소문자 숫자만 입력 가능합니다.';
    return;
  }
  if (lenOfInput == 0) {
    $errPw.classList.remove('hidden');
    $errPw.style = 'color : red';
    $errPw.textContent = '* 비밀번호를 입력해 주세요.';
  } else if (8 > lenOfInput || lenOfInput > 20) {
    $errPw.classList.remove('hidden');
    $errPw.style = 'color : red';
    $errPw.textContent = '* 비밀번호는 8~20자 입력 가능합니다.';
  } else {
    $errPw.classList.add('hidden');
    $errPw.textContent = '';
    pwCheck_h(e);
  }
  return;
});

$pwCheck.addEventListener('focus', e => {
  if ($pw.value == null) {
    $pw.focus();
  } else if (!$errPw.classList.contains('hidden')) {
    $pw.focus();
  };
  return;
});

//비밀번호 확인
$pwCheck.addEventListener('input', pwCheck_h);

//비밀번호 확인
$pwCheck.addEventListener('keydown', e => {
  const input = $pwCheck.value;
  const inputChk = $pw.value;
  const lenOfInput = input.length;

  if (e.key === ' ') {
    e.preventDefault();
  }

  if (e.key != 'Enter') {
    return;
  }

  if (e.key == 'Enter') {
    if (lenOfInput != 0 && input == inputChk) {
      $email.focus();
    }
  }
  return;
});

// 비밀번호 변경 버튼
myButton.addEventListener('click',toggleDiv);

//신규 비밀번호 저장
const updatePw = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      alert('비밀번호 수정 성공!');
    } else {
      alert('비밀번호 수정 실패 ㅠㅠ');
    }
  } else {
    alert(`${res.header.rtmsg}`);
  };
  return;
};

const updatePw_h = () => {
  const url = `/api/mypage/updatePw?pw=${$pw.value}`;
  ajax
    .get(url)
    .then(res => res.json())
    .then(updatePw) //res=>chkEmail(res)
    .catch(console.error); //err=>console.error(err)
  return;
};

myButtonCom.addEventListener('click',e=>{
    if(passwordError.style.color === 'green' &&
        $errPwCheck.style.color === 'green'){
            //저장
            updatePw_h();
            toggleOut();
        }
});

function toggleDiv() {
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
