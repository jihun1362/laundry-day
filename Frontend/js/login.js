const loginBtn = document.querySelector('.login_btn button');

loginBtn.addEventListener('click', login);

function login(e) {
  e.preventDefault();
  
  // 사용자 입력 데이터 가져오기
  const email = document.querySelector('#email').value;
  const pw = document.querySelector('#password').value;

  // 입력 데이터 유효성 검사
  // 값이 비어있는 경우
  if (!email || !pw) {
    showAlert('이메일과 비밀번호를 입력해주세요.');
    return;
  }

  // 로그인 API 호출
  const formData = {
    email: email,
    password: pw
  };

  fetch('http://3.35.18.15:8080/api/users/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(formData)
  })
    .then(res => res.json())
    .then(data => {
      if (data.statusCode === 200) {
        const token = data.Authorization;

        // 로그인 성공
        showAlert('로그인되었습니다.');
        setTokenCookie(token); // 토큰을 쿠키에 저장
        redirectToMainPage(); // 메인 페이지로 이동

      } else {
        showAlert(data.msg);
      }
      console.log(data);  
    })
    .catch(error => {
      console.error('로그인 API 오류:', error);
    });
}

// 로그인 시 토큰을 쿠키에 저장
function setTokenCookie(token) {
  const expirationDate = new Date();
  expirationDate.setDate(expirationDate.getDate() + 7);

  document.cookie = `token=${token}; expires=${expirationDate.toUTCString()}; path=/`;
}

// 경고 메시지 표시 함수
function showAlert(message) {
  alert(message);
}

// 메인 페이지로 이동 함수
function redirectToMainPage() {
  window.location.href = '/Frontend/index.html';
}
