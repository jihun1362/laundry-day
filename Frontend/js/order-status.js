/**
 * 현재 날짜
 */
const dateEl = document.getElementById('date');
// 현재 날짜 가져오기
const currentDate = new Date();

// 날짜 형식 지정
const options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'short' };
const weekdayOptions = { weekday: 'short' };

// 현재 날짜를 원하는 형식으로 변환하여 표시
const formattedDate = currentDate.toLocaleDateString('ko-KR', options);
const weekday = currentDate.toLocaleDateString('ko-KR', weekdayOptions);

const formattedWeekday = `${weekday}`;
const displayDate = formattedDate.replace(weekday, formattedWeekday);

dateEl.textContent = displayDate;

/**
 * 탭 메뉴
 */
const tabMenu = document.querySelector('.tab-menu'); 
const tabMenuLis = document.querySelectorAll('.tab-menu > li'); 
const tabContentDivs = document.querySelectorAll('.tab-content > div'); 

// 진행상태 탭을 기본으로 열어두기 위해 초기 선택상태로 설정
tabMenuLis[0].classList.add('on'); // 진행상태 탭 메뉴 항목에 'on' 클래스 추가
tabContentDivs[0].classList.add('on'); // 진행상태 탭 내용에 'on' 클래스 추가

tabMenu.addEventListener('click', clickTabMenu); 

function clickTabMenu(e) {
  e.preventDefault(); 
  if (e.target.tagName !== 'A') return; 

  const targetLiEl = e.target.parentNode.parentNode; 
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






	