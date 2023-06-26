import { getAddress, saveAddress, registerCard, getCard, submitLaundryRequest, setCardId } from './api/laundry-request-api.js';

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

  /**
   * 페이지 로드 시 주소 등록
   */
  try {
    // 세션 스토리지에서 customerKey와 authKey 값을 가져오기
    var customerKey = sessionStorage.getItem('customerKey');
    var authKey = sessionStorage.getItem('authKey');

    // customerKey와 authKey 값이 존재하는 경우, 카드 등록을 실행
    if (customerKey && authKey) {
      const cardData = {
        customerKey: customerKey,
        authKey: authKey,
      };

      // 카드 등록 API 호출
      const registerResponse = await registerCard(cardData);
      console.log('카드 등록이 성공적으로 저장되었습니다.', registerResponse);

      // 성공적으로 카드를 등록했으면 세션 스토리지에서 customerKey, authKey 제거
      sessionStorage.removeItem('customerKey');
      sessionStorage.removeItem('authKey');
    }
  } catch (error) {
    console.error('카드 등록 중 오류가 발생했습니다.:', error);
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
  userAddressDetail.textContent = addressDetail;

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

/**
 * 결제수단
 */

// 자동결제할 카드등록
var clientKey = 'test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq'
var tossPayments = TossPayments(clientKey)

// 결제수단 모달에서 결제수단 추가하기 버튼 누르면
const paymentAddCardBtn = document.querySelector('.payment-add-card');
paymentAddCardBtn.addEventListener('click', openTossPayments);

// 카드 빌링키 발급 요청
async function openTossPayments() {
  tossPayments.requestBillingAuth('카드', { // 결제수단 파라미터
    // 빌링키 발급 요청을 위한 파라미터
    customerKey: 'YPGHfJkq0OE4gitafS83',
    successUrl: 'http://laundry-day.site/views/payment-success.html', // 결제 성공 페이지 URL
    failUrl: 'http://laundry-day.site/views/payment-failure.html', // 결제 실패 페이지 URL
  })
  .catch(function (error) {
    if (error.code === 'USER_CANCEL') {
      // 결제 고객이 결제창을 닫았을 때 에러 처리
    } else if (error.code === 'INVALID_CARD_COMPANY') {
      // 유효하지 않은 카드 코드에 대한 에러 처리
    }
  });
}

// 결제수단 (모달)
const paymentBtn = document.querySelector('.payment-modify'); // 결제수단 변경 버튼
const paymentModalContainer = document.querySelector('.payment-modal-container'); // 결제수단 모달
const paymentModalCloseBtn = document.querySelector('.payment-modal-close-btn'); // 결제수단 모달 닫기 버튼

paymentBtn.addEventListener('click', openPaymentModal);
paymentModalCloseBtn.addEventListener('click', hidePaymentModal);

async function openPaymentModal() {
  paymentModalContainer.style.display = 'block'; // 결제수단 모달 열기

  try {
    // 카드 조회 API 호출
    const cardInfoData = await getCard();
    console.log('카드 정보를 불러왔습니다:', cardInfoData);

    if (cardInfoData && cardInfoData.data) {
      renderCardList(cardInfoData.data);
    }
  } catch (error) {
    console.error('카드 조회 중 오류가 발생했습니다:', error);
  }
}

function hidePaymentModal() {
  paymentModalContainer.style.display = 'none'; // 결제수단 모달 닫기
  updateSelectedPaymentCard(); // 결제수단 모달 닫을 때 선택된 카드를 업데이트
}

// 등록된 카드 조회 목록 렌더링
function renderCardList(cards) {
  const myCards = cards;
  const countCard = document.querySelector('.payment-section h4');
  const cardList = document.querySelector('.card-list');

  // 기존에 렌더링된 카드 정보 삭제
  while (cardList.firstChild) {
    cardList.firstChild.remove();
  }
  let cardListNumber = countCardList(myCards);
  countCard.textContent = `등록된 카드 목록 (${cardListNumber})`;

  for (let card of myCards) {
    let cardNumber = formatCardNumber(card.cardNumber);
    cardList.insertAdjacentHTML('beforeend', 
    `
      <li>
        <input type="radio" name="payment-method" id="card" value="${card.cardId}">
        <label for="card" class="card-name">${card.cardCompany}카드</label>
        <span class="card-number">${cardNumber}</span>
      </li>
    `);
  }

  // 모달에서 선택된 radio 버튼에 배경색 스타일 적용
  const allLiElements = document.querySelectorAll('.card-list-section > .card-list > li'); 

  allLiElements.forEach(li => {
    const radioButton = li.querySelector('input[type="radio"]'); 
    const cardId = radioButton.value;

    li.addEventListener('click', () => {
      allLiElements.forEach(card => card.classList.remove('selected'));
      li.classList.add('selected');
      radioButton.checked = true;

      // cardId 값을 laundry-request-api.js 파일의 함수로 전달
      setCardId(cardId);
    });
  });
}

function countCardList(cardList) {
  let counttedCardList = 0;
  for(let i = 0; i < cardList.length; i++) {
    counttedCardList++;
  }
  return counttedCardList;
}

function formatCardNumber(cardNumber) {
  let formattedCardNumber = "";
  for(let i = 0; i < cardNumber.length; i += 4) {
    formattedCardNumber += cardNumber.substr(i, 4) + "-";
  }
  return formattedCardNumber.slice(0, -1); // 마지막 "-" 제거
}

// 모달에서 선택된 카드를 업데이트하는 함수
function updateSelectedPaymentCard() {
  const selectedRadioButton = document.querySelector('input[name="payment-method"]:checked'); // 모달에서 선택된 라디오 버튼
  const selectedPaymentCard = document.querySelector('.selected-payment-card'); // 선택된 카드 이름을 표시할 요소
  const selectedPaymentCardNumber = document.querySelector('.selected-payment-card-number'); // 선택된 카드 번호를 표시할 요소
  
  if (selectedRadioButton) {
    const selectedCardName = selectedRadioButton.nextElementSibling.textContent;
    const selectedCardNumber = selectedRadioButton.nextElementSibling.nextElementSibling.textContent;
    selectedPaymentCard.textContent = selectedCardName;
    selectedPaymentCardNumber.textContent = selectedCardNumber;
  } else {
    selectedPaymentCard.textContent = '등록한 카드가 없음';
    selectedPaymentCardNumber.textContent = '';
  }
}

// 변경 버튼 클릭 시 선택된 카드를 업데이트
const paymentModifyBtn = document.querySelector('.payment-modify');
paymentModifyBtn.addEventListener('click', updateSelectedPaymentCard);

// 모달 닫기 버튼 클릭 시 선택된 카드를 업데이트
paymentModalCloseBtn.addEventListener('click', () => {
  updateSelectedPaymentCard();
  hidePaymentModal();
});

// 페이지 로드 시 초기 선택된 카드를 업데이트
window.addEventListener('load', updateSelectedPaymentCard);

/**
 * 세탁 고지사항 동의
 */ 

function validateTerms() {
  const termsCheckbox = document.querySelector('#terms-checkbox');
  const termValidationMsg = document.querySelector('.term-validation');

  if (!termsCheckbox.checked) {
    termValidationMsg.classList.add('show');
    return false;
  }

  termValidationMsg.classList.remove('show');
  return true;
}


/**
 * 세탁신청
 */

// 세탁 유형 저장할 변수
let selectedLaundryType = '';

// 아코디언 메뉴
const accordionHeaders = document.querySelectorAll('.accordion-header'); // 세탁 서비스 유형
accordionHeaders.forEach((header, i) => {
  header.addEventListener('click', () => {
    const contentEl = document.getElementById(`accordion-content-${i}`);
    const arrowBtn = header.querySelector('.material-symbols-outlined');

    if (contentEl.style.display === 'none') {
      contentEl.style.display = 'block';
      header.style.backgroundColor = '#eff7fd';
      header.style.color = '#0d6bbd';
      arrowBtn.classList.add('on');

      // 세탁 유형 설정
      selectedLaundryType = header.textContent.trim().replace('keyboard_arrow_down', '');
    } else {
      contentEl.style.display = 'none';
      header.style.backgroundColor = '#fff';
      header.style.color = '#000';
      arrowBtn.classList.remove('on');
    }
  });
});

// 체크박스에서 선택된 세탁물 종류 가져오기
function getWashingMethods() {
  const checkboxes = document.querySelectorAll('.accordion-content input[type=checkbox]');
  const selectedMethods = [];
  
  checkboxes.forEach((checkbox) => {
    if (checkbox.checked) {
      selectedMethods.push(checkbox.parentNode.textContent.trim());  // 체크박스의 textContent 값이 세탁 방법의 이름이라고 가정
    }
  });

  return selectedMethods.join('/');  // 선택된 세탁물 종류를 "/"로 연결
}

// textarea
const inputText = document.querySelector('textarea'); // 요청사항
const counterSpan = document.querySelector('.counter > span');
const counterStrong = document.querySelector('.counter > strong');

counterStrong.textContent = ` / ${inputText.maxLength}`;

inputText.addEventListener('keyup', textCounter);

function textCounter() {
  counterSpan.textContent = inputText.value.length;
}

// 포인트 사용 여부를 추적하는 변수, 초기 값은 0으로 설정
let pointCheck = 0;

const toggleBnts = document.querySelectorAll('.point-toggle-button');

toggleBnts.forEach(button => {
  button.addEventListener('click', () => {
    button.classList.toggle('active');
    const toggleBtn = button.querySelector('.material-symbols-outlined');
    const isChecked = button.classList.contains('active');

    if (isChecked) {
      toggleBtn.style.color = '#8ac4f7'; // 색상 변경
      toggleBtn.textContent = 'toggle_on';
      pointCheck = 1; // 사용자가 포인트를 사용하기로 결정했으므로 1로 설정
    } else {
      toggleBtn.style.color = '#d5d3d3'; // 색상 변경
      toggleBtn.textContent = 'toggle_off';
      pointCheck = 0; // 사용자가 포인트 사용을 취소했으므로 0으로 설정
    }
    console.log(pointCheck);
  });
});


const submitRequestBtn = document.querySelector('.request-btn');

// 수거 신청하기 버튼 누른 후,
submitRequestBtn.addEventListener('click', function(e) {
  e.preventDefault();

  // 약관 동의 체크
  const termsValidated = validateTerms();

  if (termsValidated) {
    // 약관 동의가 완료된 경우에만 수거 신청 처리
    const laundryType = selectedLaundryType; // 세탁 서비스 유형
    const washingMethod = getWashingMethods(); // 세탁물 종류
    const orderRequest = inputText.value; // 사용자가 입력한 요청사항
    const usePointCheck = pointCheck; // 포인트 사용 여부

    const formRequestData = {
      laundryType: laundryType,
      washingMethod: washingMethod,
      orderRequest: orderRequest,
      usePointCheck: usePointCheck
    }

    // 세탁 신청 API 호출
    const sendLaundryRequest = async () => {
      try {
        const responseData = await submitLaundryRequest(formRequestData);
        console.log('세탁 신청이 완료되었습니다.', responseData);
        alert('수거 신청이 완료되었습니다.');
        window.location.href = 'http://laundry-day.site/index.html';
      } catch (error) {
        console.error('세탁 신청이 실패했습니다:', error);
        alert('세탁 신청에 실패했습니다. 다시 시도해주세요.');
      }
    }

    sendLaundryRequest();
  }

});
