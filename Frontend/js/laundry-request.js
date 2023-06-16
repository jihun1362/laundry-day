// 아코디언 메뉴
const accordionHeaders = document.querySelectorAll('.accordion-header');
accordionHeaders.forEach((header, i) => {
  header.addEventListener('click', () => {
    const contentEl = document.getElementById(`accordion-content-${i}`);
    const arrowBtn = header.querySelector('.material-symbols-outlined');

    if (contentEl.style.display === 'none') {
      contentEl.style.display = 'block';
      header.style.backgroundColor = '#eff7fd';
      header.style.color = '#0d6bbd';
      arrowBtn.classList.add('on');
    } else {
      contentEl.style.display = 'none';
      header.style.backgroundColor = '#fff';
      header.style.color = '#000';
      arrowBtn.classList.remove('on');
    }
  });
});

// textarea
const inputText = document.querySelector('textarea');
const counterSpan = document.querySelector('.counter > span');
const counterStrong = document.querySelector('.counter > strong');

counterStrong.textContent = ` / ${inputText.maxLength}`;

inputText.addEventListener('keyup', textCounter);

function textCounter() {
  counterSpan.textContent = inputText.value.length;
}

// 결제수단
const toggleBnts = document.querySelectorAll('.point-toggle-button');
const pointAmount = document.querySelector('.point-amount');

toggleBnts.forEach(button => {
  button.addEventListener('click', () => {
    button.classList.toggle('active');
    const toggleBtn = button.querySelector('.material-symbols-outlined');
    const isChecked = button.classList.contains('active');
    
    if (isChecked) {
      toggleBtn.style.color = '#8ac4f7'; // 색상 변경
      toggleBtn.textContent = 'toggle_on';
    } else {
      toggleBtn.style.color = '#d5d3d3'; // 색상 변경
      toggleBtn.textContent = 'toggle_off';
    }
  });
});

// 보유 포인트 업데이트
// 예시로 1000점을 가정
pointAmount.textContent = '1000';

