/**
 * 탭 메뉴
 */
const tabMenu = document.querySelector('.tab-menu'); 
const tabMenuLis = document.querySelectorAll('.tab-menu > li'); 
const tabContentDivs = document.querySelectorAll('.tab-content > div'); 

// 회원정보 탭을 기본으로 열어두기 위해 초기 선택상태로 설정
tabMenuLis[0].classList.add('on'); // 회원정보 탭 메뉴 항목에 'on' 클래스 추가
tabContentDivs[0].classList.add('on'); // 회원정보 탭 내용에 'on' 클래스 추가

tabMenu.addEventListener('click', clickTabMenu); 

function clickTabMenu(e) {
  e.preventDefault(); 
  if (e.target.tagName !== 'A') return; 

  const targetLiEl = e.target.parentNode; 
  tabMenuLis.forEach((item) => {
    item.classList.remove('on'); // 모든 탭 메뉴 항목에서 'on' 클래스를 제거
  });

  tabContentDivs.forEach((item) => {
    item.classList.remove('on'); // 모든 탭 내용에서 'on' 클래스를 제거
  });

  // 선택된 탭 메뉴 항목에 해당하는 탭 내용을 찾아 'on' 클래스를 추가
  document.querySelector(`.tab-content > .c_${targetLiEl.className.substring(2)}`).classList.add('on');
  
  targetLiEl.classList.add('on'); // 선택된 탭 메뉴 항목에 'on' 클래스를 추가
}

// 회원정보 로그아웃
const mypageLogout = document.querySelector('.mypage-logout');
mypageLogout.addEventListener('click', logout);

function logout(e) {
  
  e.preventDefault();
  // 쿠키에서 토큰 제거
  document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
  // 로그아웃 후 리다이렉트 또는 필요한 동작 수행
  window.location.href = 'http://laundry-day.site/views/login.html'; // 로그인 페이지로 리다이렉트
}