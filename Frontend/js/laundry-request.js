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

/**
 * 주소 등록 (모달)
 */
const addressBtn = document.querySelector('.address-modify');
const addressModalContainer = document.querySelector('.address-modal-container');
const addressModalSaveBtn = document.querySelector('.modal-actions');
const userAddress = document.querySelector('.user-address');

addressBtn.addEventListener('click', openAddressModal);
addressModalSaveBtn.addEventListener('click', saveAddressModal);

function openAddressModal() {
  addressModalContainer.style.display = 'block';
}

function saveAddressModal(e) {
  e.preventDefault();

  const addressInput = document.getElementById('address-input');
  const addressDetailsInput = document.getElementById('address-details-input');

  userAddress.textContent = `${addressInput.value} ${addressDetailsInput.value}`;

  addressModalContainer.style.display = 'none';
}

const deliveryMethodRadio = document.querySelectorAll('.delivery-method-section input[name="delivery-method"]');
const accessMethod = document.querySelector('.access-method');
const detailInput = document.getElementById('detail-input');
const userAddressDetail = document.querySelector('.user-address-detail');

for (let i = 0; i < deliveryMethodRadio.length; i++) {
  deliveryMethodRadio[i].addEventListener('change', updateAccessMethod);
}

function updateAccessMethod() {
  for (let i = 0; i < deliveryMethodRadio.length; i++) {
    if (deliveryMethodRadio[i].checked) {
      accessMethod.textContent = deliveryMethodRadio[i].nextSibling.textContent;
      break;
    }
  }
}

detailInput.addEventListener('input', updateAddressDetail);

function updateAddressDetail() {
  userAddressDetail.textContent = detailInput.value;
}

/**
 * 결제수단 (모달)
 */
const paymentBtn = document.querySelector('.payment-modify');
const paymentModalContainer = document.querySelector('.payment-modal-container');
const paymentModalCloseBtn = document.querySelector('.payment-modal-close-btn');

paymentBtn.addEventListener('click', openPaymentModal);
paymentModalCloseBtn.addEventListener('click', hidePaymentModal);

function openPaymentModal() {
  paymentModalContainer.style.display = 'block';
}

function hidePaymentModal() {
  paymentModalContainer.style.display = 'none';
  updateSelectedPaymentCard(); // 모달 닫을 때 라벨 업데이트
}

// 모달에서 선택된 radio 버튼에 배경색 스타일 적용
const radioButtons = document.querySelectorAll('.card-list-section > .card-list > li > input[type="radio"]');
const allLiElements = document.querySelectorAll('.card-list-section > .card-list > li');

radioButtons.forEach(function(radioButton) {
  radioButton.addEventListener('change', function() {
    allLiElements.forEach(function(liElement) {
      const liRadioButton = liElement.querySelector('input[type="radio"]');
      if (liRadioButton !== radioButton) {
        liElement.style.backgroundColor = '#fff';
        liRadioButton.checked = false;
      }
    });

    const selectedLi = this.parentNode;
    if (this.checked) {
      selectedLi.style.backgroundColor = '#01c1f61d';
      this.checked = true;
    }
  });
});

// 모달에서 선택된 카드를 업데이트하는 함수
function updateSelectedPaymentCard() {
  const selectedRadioButton = document.querySelector('input[name="payment-method"]:checked');
  const selectedCardName = selectedRadioButton.nextElementSibling.textContent;
  const selectedCardNumber = selectedRadioButton.nextElementSibling.nextElementSibling.textContent;
  const selectedPaymentCard = document.querySelector('.selected-payment-card');
  const selectedPaymentCardNumber = document.querySelector('.selected-payment-card-number');
  selectedPaymentCard.textContent = selectedCardName;
  selectedPaymentCardNumber.textContent = selectedCardNumber;
}

// 변경 버튼 클릭 시 선택된 카드를 업데이트
const paymentModifyBtn = document.querySelector('.payment-modify');
paymentModifyBtn.addEventListener('click', updateSelectedPaymentCard);

// 모달 닫기 버튼 클릭 시 선택된 카드를 업데이트
paymentModalCloseBtn.addEventListener('click', hidePaymentModal);

// 페이지 로드 시 초기 선택된 카드를 업데이트
window.addEventListener('load', updateSelectedPaymentCard);

// 초기 로드 시 checked 속성이 있는 라디오 버튼에 대해서 배경색 적용
radioButtons.forEach(function(radioButton) {
  if (radioButton.checked) {
    const selectedLi = radioButton.parentNode;
    selectedLi.style.backgroundColor = '#01c1f61d';
  }
});

