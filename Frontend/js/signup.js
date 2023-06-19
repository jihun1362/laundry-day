const submitBtn = document.querySelector('.signup-btn');
const emailInput = document.querySelector('#user_email');
const pwInput = document.querySelector('#user_pw');
const pwConfirm = document.querySelector('#pwConfirm');
const nameInput = document.querySelector('#user_name');
const phoneInput = document.querySelector('#user_phone');
const termCheckbox = document.querySelector('#term');
const termsCheckbox = document.querySelector('#terms');

// 회원 가입하기 버튼 누른 후,
submitBtn.addEventListener('click', function(e) {
  e.preventDefault();

  const isValidEmail = validateEmail();
  const isValidPassword = validatePassword();
  const isValidPwConfirmation = validatePwConfirmation();
  const isValidName = validateName();
  const isValidPhone = validatePhone();
  const isValidTerms = validateTerms();

  if (!isValidEmail || !isValidPassword || !isValidPwConfirmation || !isValidName || !isValidPhone || !isValidTerms) {
    showAlert('입력값이 올바르지 않습니다.');
    return;
  }

  // ⭐️추후, 폼 전송 또는 처리 로직 수행⭐️

  // 회원가입 처리 로직
  const formData = {
    email: emailInput.value,
    // password: hashPassword(pwInput.value), // 비밀번호 암호화하여 전송
    password: pwInput.value,
    nickname: nameInput.value,
    phoneNumber: phoneInput.value
  };

  // 폼 데이터 JSON으로 변환
  const jsonData = JSON.stringify(formData);

  fetch('http://3.35.18.15:8080/api/users/signup', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: jsonData
  })
    .then(res => res.json())
    .then(data => {
      if (data.data === 'DUPLICATE_EMAIL_ERROE' || data.data === 'DUPLICATE_PHONENUMBER_ERROE') {
        showAlert(`${data.msg}`);
      } else {
        // 회원가입 성공 시, 회원가입 완료 페이지로 이동
        window.location.href = 'signup-complete.html';
      }
      console.log(data);  
    })    
    .catch(error => {
      console.error('Signup API error:', error);
    });
});

function showAlert(message) {
  alert(message);
}

// 폼 데이터 전송 함수
function sendFormData(formData) {
  // 테스트 확인용
  console.log(formData);
}

/**
 * 이메일
 */
function validateEmail() {
  const emailValidationMsg = document.querySelector('.email-validation');
  const emailPattern = /^[A-Za-z0-9_\\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+$/;

  if (emailInput.value === '') {
    emailInput.classList.add('on');
    emailValidationMsg.classList.add('show');
    return false;
  }

  if (!emailPattern.test(emailInput.value)) {
    emailInput.classList.add('on');
    emailValidationMsg.classList.add('show');
    return false;
  }

  emailInput.classList.remove('on');
  emailValidationMsg.classList.remove('show');
  return true;

  // ⭐️추후, 이메일 인증하기 수행⭐️
}

/**
 * 비밀번호
 */
function validatePassword() {
  const pwValidationMsg = document.querySelector('.pw-validation');
  const pwPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&+=])[A-Za-z\d!@#$%^&+=]{8,}$/;

  if (pwInput.value === '') {
    pwInput.classList.add('on');
    pwValidationMsg.classList.add('show');
    return false;
  }

  if (!pwPattern.test(pwInput.value)) {
    pwInput.classList.add('on');
    pwValidationMsg.classList.remove('show');
    document.querySelector('.pw-validation.error-pw-msg').classList.add('show');
    return false;
  }

  pwInput.classList.remove('on');
  pwValidationMsg.classList.remove('show');
  document.querySelector('.pw-validation.error-pw-msg').classList.remove('show');
  return true;
}

/**
 * 비밀번호 확인
 */
function validatePwConfirmation() {
  const pwMismatchMsg = document.querySelector('.pw-match');

  if (pwConfirm.value !== pwInput.value) {
    pwConfirm.classList.add('on');
    pwMismatchMsg.classList.add('show');
    return false;
  }

  pwConfirm.classList.remove('on');
  pwMismatchMsg.classList.remove('show');
  return true;
}

/**
 * 이름
 */
function validateName() {
  const nameValidationMsg = document.querySelector('.name-validation');

  if (nameInput.value === '') {
    nameInput.classList.add('on');
    nameValidationMsg.classList.add('show');
    return false;
  }

  nameInput.classList.remove('on');
  nameValidationMsg.classList.remove('show');
  return true;
}

/**
 * 휴대폰 번호
 */
function validatePhone() {
  const phoneValidationMsg = document.querySelector('.phone_input .error-msg');
  const phoneValidationErrMsg = document.querySelector('.phone_input .error-phone-msg');
  const phonePattern = /^\d{3}-\d{3,4}-\d{4}$/;

  if (phoneInput.value === '') {
    phoneInput.classList.add('on');
    phoneValidationMsg.classList.add('show');
    return false;
  }

  if (!phonePattern.test(phoneInput.value)) {
    phoneInput.classList.add('on');
    phoneValidationErrMsg.classList.remove('show');
    phoneValidationMsg.classList.add('show');
    return false;
  }

  phoneInput.classList.remove('on');
  phoneValidationMsg.classList.remove('show');
  phoneValidationErrMsg.classList.remove('show');
  return true;

  // ⭐️추후, 휴대폰 인증하기 수행⭐️
}

/**
 * 약관 동의
 */
function validateTerms() {
  const termCheckboxes = document.querySelectorAll('input[type="checkbox"]');
  const termValidationMsg = document.querySelector('.term-validation');

  for (let i = 0; i < termCheckboxes.length; i++) {
    if (!termCheckboxes[i].checked) {
      termValidationMsg.classList.add('show');
      return false;
    }
  }

  termValidationMsg.classList.remove('show');
  return true;
}

// 입력 필드 값이 변경될 때 에러 메시지 제거
emailInput.addEventListener('input', () => {
  emailInput.classList.remove('on');
  document.querySelector('.email-validation').classList.remove('show');
});

pwInput.addEventListener('input', () => {
  pwInput.classList.remove('on');
  document.querySelector('.pw-validation').classList.remove('show');
  document.querySelector('.pw-validation.error-pw-msg').classList.remove('show');
});

pwConfirm.addEventListener('input', () => {
  pwConfirm.classList.remove('on');
  document.querySelector('.pw-match').classList.remove('show');
});

nameInput.addEventListener('input', () => {
  nameInput.classList.remove('on');
  document.querySelector('.name-validation').classList.remove('show');
});

phoneInput.addEventListener('input', () => {
  phoneInput.classList.remove('on');
  document.querySelector('.phone_input .error-msg').classList.remove('show');
  document.querySelector('.phone_input .error-phone-msg').classList.remove('show');
});

termCheckbox.addEventListener('input', () => {
  document.querySelector('.term-validation').classList.remove('show');
});

termsCheckbox.addEventListener('input', () => {
  document.querySelector('.term-validation').classList.remove('show');
});
