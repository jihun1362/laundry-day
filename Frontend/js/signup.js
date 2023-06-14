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

  if (!validateEmail() || !validatePassword() || !validatePwConfirmation() || !validateName() || !validatePhone() || !validateTerms()) {
    showAlert('입력값이 올바르지 않습니다.');
    return;
  }

  // ⭐️추후, 폼 전송 또는 처리 로직 수행⭐️

  // 회원가입 처리 로직
  const formData = {
    email: emailInput.value,
    password: hashPassword(pwInput.value), // 비밀번호 암호화하여 전송
    name: nameInput.value,
    phone: phoneInput.value
  };

  // 폼 데이터 전송
  sendFormData(formData);
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
 * 비밀번호 해시화 함수
 */
function hashPassword(password) {
  // CryptoJS 라이브러리를 사용하여 SHA-256 단방향 해시 함수를 적용
  const hashedPassword = CryptoJS.SHA256(password).toString();
  return hashedPassword;
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
