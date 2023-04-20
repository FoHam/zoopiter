import { ajax } from '/js/ajax.js';
// 닉네임체크
const nicknameInput = document.getElementById('nickname');
const nicknameError = document.getElementById('nickname-error');
const btnModify = document.getElementById('btn_modify');

//<!--사이드메뉴 강조-->
$(function () {
  $('#mypagePcGnb').children().eq(0).find('a').addClass('on');
});

//닉네임 저장
const updateNick = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      location.href = '/mypage';
    } else {
      alert('닉네임 저장 실패');
    }
  } else {
    alert(`${res.header.rtmsg}`);
  }
  return;
};

const updateNick_h = () => {
  const url = `/api/mypage/updateNick?nick=${nicknameInput.value}`;
  ajax
    .get(url)
    .then(res => res.json())
    .then(updateNick) //res=>chkEmail(res)
    .catch(console.error); //err=>console.error(err)
  return;
};

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
      updateNick_h();
    }
  });
}

btnModify.addEventListener('click', e => {
  if (nicknameError.style.color === 'green') {
    modify();
  } else {
    location.href = '/mypage';
  }
  return;
});

//닉네임 중복체크
const chkNick = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      nicknameError.style = 'color : red; font-size: 14px';
      nicknameError.textContent = '사용중인 닉네임 입니다.';
    } else {
      nicknameError.style = 'color : green; font-size: 14px';
      nicknameError.textContent = '사용가능한 닉네임 입니다.';
    }
  } else {
    nicknameError.textContent = `${res.header.rtmsg}`;
  }
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
      nicknameError.style = 'color : red; font-size: 14px';
      nicknameError.textContent = '* 영문 숫자 한글만 입력 가능합니다.';
      nicknameInput.focus();
      return;
    }
    if (lenOfInput == 0) {
      nicknameError.classList.remove('hidden');
      nicknameError.style = 'color : red; font-size: 14px';
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
    nicknameError.style = 'color : red; font-size: 14px';
    nicknameError.textContent = '* 영문 숫자 한글만 입력 가능합니다.';
    return;
  }
  if (lenOfInput == 0) {
    nicknameError.classList.remove('hidden');
    nicknameError.style = 'color : red; font-size: 14px';
    nicknameError.textContent = '* 닉네임을 입력해 주세요.';
    nicknameInput.value = '';
  } else {
    //    $errNickname.classList.add('hidden');
    chkNick_h();
    // $nickname.value = input;
  }
  return;
});

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
      passwordError.style = 'color : green; font-size: 14px';
      passwordError.textContent = '비밀번호가 맞습니다';
    } else {
      passwordError.style = 'color : red; font-size: 14px';
      passwordError.textContent = '비밀번호가 틀립니다';
    }
  } else {
    $errNickname.textContent = `${res.header.rtmsg}`;
  }
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

myPwChk.addEventListener('click', isExistPw_h);

//비밀번호 확인 함수
const pwCheck_h = e => {
  const input = $pwCheck.value;
  const inputChk = $pw.value;
  const lenOfInput = input.length;

  if (lenOfInput != 0) {
    if (input == inputChk) {
      $errPw.classList.add('hidden');
      $errPwCheck.style = 'color : green; font-size: 14px';
      $errPwCheck.textContent = '비밀번호가 일치합니다';
    } else {
      $errPwCheck.classList.remove('hidden');
      $errPwCheck.style = 'color : red; font-size: 14px';
      $errPwCheck.textContent = '비밀번호가 일치하지 않습니다.';
    }
  }
  return;
};

//비밀번호
$pw.addEventListener('keydown', e => {
  const input = $pw.value;
  const lenOfInput = input.length;
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,20}$/;
  if (e.key === ' ') {
    e.preventDefault();
  }

  if (e.key != 'Enter') {
    return;
  }
  if (e.key == 'Enter') {
    if (/[^A-Za-z0-9]/.test(input)) {
      $errPw.classList.remove('hidden');
      $errPw.style = 'color : red; font-size: 14px';
      $errPw.textContent = '* 영문 대소문자 숫자만 입력 가능합니다.';
      return;
    }
    if (!passwordRegex.test(input)) {
      $errPw.classList.remove('hidden');
      $errPw.style = 'color : red; font-size: 14px';
      $errPw.textContent =
        '* 영문 대문자, 소문자 ,숫자가 모두 포함되어야 합니다.';
      $pw.focus();
      return;
    }
    if (lenOfInput == 0) {
      $errPw.classList.remove('hidden');
      $errPw.style = 'color : red; font-size: 14px';
      $errPw.textContent = '* 비밀번호를 입력해 주세요.';
      $pw.focus();
    } else if (8 > lenOfInput || lenOfInput > 20) {
      $errPw.classList.remove('hidden');
      $errPw.style = 'color : red; font-size: 14px';
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
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,20}$/;

  if (/[^A-Za-z0-9]/.test(input)) {
    $errPw.classList.remove('hidden');
    $errPw.style = 'color : red; font-size: 14px';
    $errPw.textContent = '* 영문 대소문자 숫자만 입력 가능합니다.';
    return;
  }
  if (!passwordRegex.test(input)) {
    $errPw.classList.remove('hidden');
    $errPw.style = 'color : red; font-size: 14px';
    $errPw.textContent =
      '* 영문 대문자, 소문자 ,숫자가 모두 포함되어야 합니다.';
    return;
  }
  if (lenOfInput == 0) {
    $errPw.classList.remove('hidden');
    $errPw.style = 'color : red; font-size: 14px';
    $errPw.textContent = '* 비밀번호를 입력해 주세요.';
  } else if (8 > lenOfInput || lenOfInput > 20) {
    $errPw.classList.remove('hidden');
    $errPw.style = 'color : red; font-size: 14px';
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
  }
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
myButton.addEventListener('click', toggleDiv);

//신규 비밀번호 저장
const updatePw = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      alert('비밀번호 수정 성공!');
    } else {
      alert('비밀번호 수정 실패');
    }
  } else {
    alert(`${res.header.rtmsg}`);
  }
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

myButtonCom.addEventListener('click', e => {
  if (
    passwordError.style.color === 'green' &&
    $errPwCheck.style.color === 'green'
  ) {
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
