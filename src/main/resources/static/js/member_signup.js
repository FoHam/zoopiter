import { ajax } from '/js/ajax.js';

const $nickname = document.getElementById('nickname');
const $id = document.getElementById('id');
const $pw = document.getElementById('pw');
const $pwCheck = document.getElementById('pwCheck');
const $email = document.getElementById('email');
const $emailCheck = document.getElementById('emailCheck');
const $emailSendBtn = document.getElementById('emailSendBtn');
const $emailSendBtn2 = document.getElementById('emailSendBtn2');

const $errNickname = document.querySelector('.err.nickname');
const $errId = document.querySelector('.err.id');
const $errPw = document.querySelector('.err.pw');
const $errPwCheck = document.querySelector('.err.pwCheck');
const $errEmail = document.querySelector('.err.email');
const $errEmailCheck = document.querySelector('.err.emailCheck');

//닉네임 중복체크
const chkNick = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      $errNickname.style = 'color : red';
      $errNickname.textContent = '사용중인 닉네임 입니다.';
    } else {
      $errNickname.style = 'color : green';
      $errNickname.textContent = '사용가능한 닉네임 입니다.';
    }
  } else {
    $errNickname.textContent = `${res.header.rtmsg}`;
  };
  return;
};

const chkNick_h = () => {
  const url = `/api/members/nickname?nickname=${$nickname.value}`;
  ajax
    .get(url)
    .then(res => res.json())
    .then(chkNick) //res=>chkEmail(res)
    .catch(console.error); //err=>console.error(err)
  return;
};

// 닉네임
$nickname.addEventListener('keydown', e => {
  const input = $nickname.value;
  const lenOfInput = input.length;

  if (e.key === ' ') {
    e.preventDefault();
  }

  if (e.key != 'Enter') {
    return;
  }
  if (e.key == 'Enter') {
    if (/[^A-Za-z0-9ㄱ-힣]/.test(input)) {
      $errNickname.classList.remove('hidden');
      $errNickname.style = 'color : red';
      $errNickname.textContent = '* 영문 숫자 한글만 입력 가능합니다.';
      $nickname.focus();
      return;
    }
    if (lenOfInput == 0) {
      $errNickname.classList.remove('hidden');
      $errNickname.style = 'color : red';
      $errNickname.textContent = '* 닉네임를 입력해 주세요.';
      $nickname.focus();
      $nickname.value = '';
    } else {
      //      $errNickname.classList.add('hidden');
      chkNick_h();
      // $nickname.value = input;
    }
    return;
  }
  return;
});

$nickname.addEventListener('blur', e => {
  const input = $nickname.value;
  const lenOfInput = input.length;

  if (/[^A-Za-z0-9ㄱ-힣]/.test(input)) {
    $errNickname.classList.remove('hidden');
    $errNickname.style = 'color : red';
    $errNickname.textContent = '* 영문 숫자 한글만 입력 가능합니다.';
    return;
  }
  if (lenOfInput == 0) {
    $errNickname.classList.remove('hidden');
    $errNickname.style = 'color : red';
    $errNickname.textContent = '* 닉네임을 입력해 주세요.';
    $nickname.value = '';
  } else {
    //    $errNickname.classList.add('hidden');
    chkNick_h();
    // $nickname.value = input;
  }
  return;
});

//아이디 중복체크
const chkId = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      $errId.style = 'color : red';
      $errId.textContent = '사용중인 아이디 입니다.';
    } else {
      $errId.style = 'color : green';
      $errId.textContent = '사용가능한 아이디 입니다.';
    }
  } else {
    $errId.textContent = `${res.header.rtmsg}`;
  };
  return;
};

const chkId_h = () => {
  $errId.style = 'color : grey';
  $errId.textContent = '아이디 중복체크중 입니다.';
  const url = `/api/members/id?id=${$id.value}`;
  ajax
    .get(url)
    .then(res => res.json())
    .then(chkId) //res=>chkEmail(res)
    .catch(console.error); //err=>console.error(err)
  return;
};

//아이디
$id.addEventListener('keydown', e => {
  const input = $id.value;
  const lenOfInput = input.length;
  if (e.key === ' ') {
    e.preventDefault();
  }

  if (e.key != 'Enter') {
    return;
  }
  if (e.key == 'Enter') {
    if (/[^A-Za-z0-9]/.test(input)) {
      $errId.classList.remove('hidden');
      $errId.style = 'color : red';
      $errId.textContent = '* 영문 숫자만 입력 가능합니다.';
      $id.focus();
      return;
    }
    if (lenOfInput == 0) {
      $errId.classList.remove('hidden');
      $errId.style = 'color : red';
      $errId.textContent = '* 아이디를 입력해 주세요.';
      $id.focus();
      $id.value = '';
    } else {
      //      $errId.classList.add('hidden');
      // $id.value = input;
      chkId_h();
    };
    return;
  };
  return;
});

$id.addEventListener('blur', e => {
  const input = $id.value;
  const lenOfInput = input.length;

  if (/[^A-Za-z0-9]/.test(input)) {
    $errId.classList.remove('hidden');
    $errId.style = 'color : red';
    $errId.textContent = '* 영문 숫자만 입력 가능합니다.';
    return;
  }
  if (lenOfInput == 0) {
    $errId.classList.remove('hidden');
    $errId.style = 'color : red';
    $errId.textContent = '* 아이디을 입력해 주세요.';
    $id.value = '';
  } else {
    //    $errId.classList.add('hidden');
    chkId_h();
    // $id.value = input;
  }
  return;
});

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
      $errPw.textContent = '* 영문 대소문자 숫자만 입력 가능합니다.';
      $pw.focus();
      return;
    }
    if (lenOfInput == 0) {
      $errPw.classList.remove('hidden');
      $errPw.textContent = '* 비밀번호를 입력해 주세요.';
      $pw.focus();
    } else if (8 > lenOfInput || lenOfInput > 20) {
      $errPw.classList.remove('hidden');
      $errPw.textContent = '* 비밀번호는 8~20자 입력 가능합니다.';
      $pw.focus();
    } else {
      $errPw.classList.add('hidden');
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
    $errPw.textContent = '* 영문 대소문자 숫자만 입력 가능합니다.';
    return;
  }
  if (lenOfInput == 0) {
    $errPw.classList.remove('hidden');
    $errPw.textContent = '* 비밀번호를 입력해 주세요.';
  } else if (8 > lenOfInput || lenOfInput > 20) {
    $errPw.classList.remove('hidden');
    $errPw.textContent = '* 비밀번호는 8~20자 입력 가능합니다.';
  } else {
    $errPw.classList.add('hidden');
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

//이메일 중복체크
const chkEmail = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      $errEmail.style = 'color : red';
      $errEmail.textContent = '사용중인 이메일 입니다.';
    } else {
      $errEmail.style = 'color : green';
      $errEmail.textContent = '사용가능한 이메일 입니다.';
    }
  } else {
    $errEmail.textContent = `${res.header.rtmsg}`;
  };
  return;
};

const chkEmail_h = () => {
  const url = `/api/members/email?email=${email.value}`;
  ajax
    .get(url)
    .then(res => res.json())
    .then(chkEmail) //res=>chkEmail(res)
    .catch(console.error); //err=>console.error(err)
};

//이메일
$email.addEventListener('keydown', e => {
  const input = $email.value;
  const lenOfInput = input.length;
  const emailRegex = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@(?:\w+\.)+\w+$/;

  if (e.key === ' ') {
    e.preventDefault();
  }

  if (e.key != 'Enter') {
    return;
  }
  if (e.key == 'Enter') {
    if (lenOfInput == 0) {
      $errEmail.classList.remove('hidden');
      $errEmail.style = 'color : red';
      $errEmail.textContent = '* 이메일를 입력해 주세요.';
      $email.focus();
      $email.value = '';
    } else if (!emailRegex.test(input)) {
      $errEmail.classList.remove('hidden');
      $errEmail.style = 'color : red';
      $errEmail.textContent = '* 이메일 양식에 맞게 입력해 주세요.';
      $email.focus();
    } else {
      //      $errEmail.classList.add('hidden');
      //      $email.value = input;
      chkEmail_h();
      if ($errEmail.style.color === 'green') {
        $emailCheck.focus();
      }
    }
    return;
  };
  return;
});

$email.addEventListener('blur', e => {
  const input = $email.value;
  const lenOfInput = input.length;
  const emailRegex = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@(?:\w+\.)+\w+$/;

  if (lenOfInput == 0) {
    $errEmail.classList.remove('hidden');
    $errEmail.style = 'color : red';
    $errEmail.textContent = '* 이메일를 입력해 주세요.';
    $email.value = '';
  } else if (!emailRegex.test(input)) {
    $errEmail.classList.remove('hidden');
    $errEmail.style = 'color : red';
    $errEmail.textContent = '* 이메일 양식에 맞게 입력해 주세요.';
  } else {
    //    $errEmail.classList.add('hidden');
    //    $email.value = input;
    chkEmail_h();
  }
  return;
});

//이메일 인증 인풋 막기
$emailCheck.addEventListener('keydown',e=>{
      if (e.key == 'Enter') {
        e.preventDefault();
      };
      return;
});

//이메일 인증 체크1
const emailSend = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      $errEmailCheck.style = 'color : grey';
      $errEmailCheck.textContent = res.data;
    } else {
      $errEmailCheck.style = 'color : red';
      $errEmailCheck.textContent = '오류';
    }
  } else {
    $errEmailCheck.textContent = `${res.header.rtmsg}`;
  };
  return;
};

const emailSendBtn_h = () => {
  const url = `/api/members/emailChk?email=${email.value}`;
  $errEmailCheck.textContent =
    '이메일을 보내고 있습니다. 잠시만 기다려 주세요.';
  document.getElementById('emailSendBtn').disabled = true;
  ajax
    .get(url)
    .then(res => res.json())
    .then(emailSend) //res=>chkEmail(res)
    .then(e=>{
      if($errEmailCheck.style.color === 'grey'){
            $emailSendBtn.classList.add('hidden');
            $emailSendBtn2.classList.remove('hidden');
      }else{
            document.getElementById('emailSendBtn').disabled = false;
      };
    })
    .catch(console.error); //err=>console.error(err)
  return;
};

$emailSendBtn.addEventListener('click', e => {
  if ($errEmail.style.color != 'green') {
    $errEmailCheck.style = 'color : red';
    $errEmailCheck.textContent = '* 이메일을 양식에 맞게 입력해 주세요';
    e.preventDefault();
  } else if($errEmail.style.color === 'green') {
    document.getElementById('email').disabled = true;
    emailSendBtn_h();
  };
  return;
});

//이메일 인증 체크2
const emailSend2 = res => {
  if (res.header.rtcd == '00') {
    if (res.data) {
      $errEmailCheck.style = 'color : green';
      $errEmailCheck.textContent = res.data;
    } else {
      $errEmailCheck.style = 'color : red';
      $errEmailCheck.textContent = '오류';
    }
  } else if (res.header.rtcd == '01') {
    $errEmailCheck.style = 'color : red';
    $errEmailCheck.textContent = res.data;
  } else {
    $errEmailCheck.textContent = `${res.header.rtmsg}`;
  };
  return;
};

const emailSendBtn_h2 = e => {
  const url = `/api/members/emailChk2?num=${emailCheck.value}`;
  ajax
    .get(url)
    .then(res => res.json())
    .then(emailSend2) //res=>chkEmail(res)
    .then(e=>{
    if($errEmailCheck.textContent=='인증성공'){
      document.getElementById('emailCheck').disabled = true;
      document.getElementById('emailSendBtn2').disabled = true;
    };
    })
    .catch(console.error); //err=>console.error(err)
    return;
};

$emailSendBtn2.addEventListener('click', e => {
  const input = $emailCheck.value;
  const lenOfInput = input.length;
  if (lenOfInput == 0) {
    e.preventDefault();
  } else {
    emailSendBtn_h2(e);
  };
  return;
});

//member 내보내기
const member_h = () => {
  const url = `/api/members/signup1`;
  const payLoad = {
    userId: $id.value,
    userPw: $pw.value,
    userNick: $nickname.value,
    userEmail: $email.value,
  };
  ajax
    .post(url, payLoad)
    .then(res => res.json())
    .catch(console.error); //err=>console.error(err)
    return;
};

const $loginBtn = document.getElementById('loginBtn');
const $loginPopup = document.getElementById('loginPopup');
$loginBtn.addEventListener('click', e => {
  if (
    $errNickname.style.color === 'green' &&
    $errId.style.color === 'green' &&
    $errEmail.style.color === 'green' &&
    $errPwCheck.style.color === 'green' &&
    $errEmailCheck.style.color === 'green'
  ) {
    member_h();
    $loginPopup.showModal();
  } else {
    alert('회원가입 양식에 맞게 입력해 주세요');
  };
  return;
});
