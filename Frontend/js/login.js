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
    .then(res => {
      const authorizationHeader = res.headers.get('Authorization');
      const token = authorizationHeader.split(' ')[1]; // 'Bearer {토큰값}'에서 토큰값만 추출
      return res.json().then(data => ({ token, data }));
    })
    .then(({ token, data }) => {
      if (data.statusCode === 200) {
        // 로그인 성공
        showAlert('로그인되었습니다.');
        setTokenCookie(token); // 토큰을 쿠키에 저장
        redirectToMainPage(); // 메인 페이지로 이동
      } else {
        showAlert(data.msg);
      }
    })
    .catch(error => {
      console.error('로그인 API 오류:', error);
    });

}

// 로그인 시 토큰을 쿠키에 저장
function setTokenCookie(token) {
  const expirationDate = new Date();
  expirationDate.setTime(expirationDate.getTime() + (1 * 60 * 60 * 1000)); // 1시간(60분 * 60초 * 1000밀리초)

  document.cookie = `token=${token}; expires=${expirationDate.toUTCString()}; path=/`;
}

// 토큰 만료 시간을 확인하고 쿠키에서 토큰 삭제
function removeTokenIfExpired() {
  const token = getCookie('token');
  const expirationDate = new Date(token);
  
  if (expirationDate && expirationDate < new Date()) {
    document.cookie = `token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
  }
}

// 경고 메시지 표시 함수
function showAlert(message) {
  alert(message);
}

// 메인 페이지로 이동 함수
function redirectToMainPage() {
  window.location.href = 'http://laundry-day.site/index.html';
}
// 실행할 코드
removeTokenIfExpired(); // 만료된 토큰이 있다면 쿠키에서 토큰 삭제
