import { getAddress, saveAddress } from './api/laundry-request-api.js';

/**
 * 페이지 로드 시 주소 조회
 */
window.addEventListener('load', async () => {
  try {
    const addressData = await getAddress();
    if (addressData && addressData.data) {
      userAddress.textContent = addressData.data.address;
      userAddressDetail.textContent = addressData.data.addressDetail;
      accessMethod.textContent = addressData.data.accessMethod;
      accessMethodDetail.textContent = addressData.data.significant;
    } else {
      // 가져온 주소 데이터가 없을 때
      userAddress.textContent = '등록된 주소가 없음';
    }
  } catch (error) {
    console.error('주소 조회에 실패했습니다:', error);
    userAddress.textContent = '등록된 주소가 없음';
  }
});


/**
 * 주소 등록 (모달)
 */
const addressBtn = document.querySelector('.address-modify'); // 수정 버튼
const addressModalContainer = document.querySelector('.address-modal-container'); // 주소 모달
const addressModalSaveBtn = document.querySelector('.modal-actions'); // 주소 저장 버튼

const userAddress = document.querySelector('.user-address'); // 주소 조회
const addressInput = document.getElementById('address-input'); // 주소 입력
const userAddressDetail = document.querySelector('.user-address-detail'); // 상세주소 조회
const addressDetailsInput = document.getElementById('address-details-input'); // 상세주소 입력

const deliveryMethodRadio = document.querySelectorAll('.delivery-method-section input[name="delivery-method"]'); // 출입방법
const accessMethod = document.querySelector('.access-method'); // 출입방법 조회
const detailInput = document.getElementById('detail-input');  // 출입방법 상세설명 입력
const accessMethodDetail = document.querySelector('.access-method-detail'); // 출입방법 상세설명 조회

addressBtn.addEventListener('click', openAddressModal);
addressModalSaveBtn.addEventListener('click', saveAddressModal);

// 수정 버튼 클릭하면 주소 모달 open
function openAddressModal() {
  // 이전에 입력한 값으로 필드 설정
  const previousAddress = userAddress.textContent;
  const previousAddressDetail = userAddressDetail.textContent;
  
  // 이전에 저장한 주소가 '등록된 주소가 없음'이라면 필드를 비워준다
  if (previousAddress === '등록된 주소가 없음') {
    addressInput.value = '';
    addressDetailsInput.value = '';
  } else {
    addressInput.value = previousAddress;
    addressDetailsInput.value = previousAddressDetail;
  }
  
  // 이전에 선택한 출입 방법 체크
  const previousAccessMethod = accessMethod.textContent;
  for (let i = 0; i < deliveryMethodRadio.length; i++) {
    if (deliveryMethodRadio[i].nextSibling.textContent === previousAccessMethod) {
      deliveryMethodRadio[i].checked = true;
      break;
    }
  }

  detailInput.value = accessMethodDetail.textContent; // 이전에 입력한 상세설명

  addressModalContainer.style.display = 'block';
}

// 출입방법 선택
for (let i = 0; i < deliveryMethodRadio.length; i++) {
  deliveryMethodRadio[i].addEventListener('change', function() {
    accessMethod.textContent = this.nextSibling.textContent;
  });
}

// 출입방법 상세설명 입력
detailInput.addEventListener('input', function() {
  accessMethodDetail.textContent = this.value;
});

// 주소 저장
async function saveAddressModal(e) {
  e.preventDefault();

  // 주소 및 출입 방법, 상세설명 업데이트
  const address = addressInput.value;
  const addressDetail = addressDetailsInput.value;
  const selectedAccessMethod = accessMethod.textContent;
  const detailDescription = detailInput.value;

  userAddress.textContent = address;
  userAddressDetail.textContent = addressDetail

  const addressData = {
    address: address,
    addressDetail: addressDetail,
    accessMethod: selectedAccessMethod,
    significant: detailDescription
  };

  try {
    // 주소 저장 API 호출
    const responseData = await saveAddress(addressData);
    console.log('주소가 성공적으로 저장되었습니다.', responseData);
  } catch (error) {
    console.error('주소 저장에 실패했습니다:', error);
  }

  // 모달 닫기
  addressModalContainer.style.display = 'none';
}

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

