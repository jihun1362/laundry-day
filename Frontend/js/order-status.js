import { getLaundryOrder } from './api/order-status-api.js';

// 페이지 로드 시 이용내역 조회
window.addEventListener('DOMContentLoaded', displayOrderStatus);

async function displayOrderStatus() {
  try {
    const orderStatusData = await getLaundryOrder();

    const status = orderStatusData.data.status; // 전체 진행상태
    const laundryResponseDtoList = orderStatusData.data.laundryResponseDtoList; // 세탁인수증 목록
    const totalStablePrice = orderStatusData.data.totalStablePrice; // 안심정찰가격
    const deliveryFee = orderStatusData.data.deliveryFee; // 수거 배송비
    const surcharge = orderStatusData.data.totalSurcharge; // 추가요금
    const usePoint = orderStatusData.data.usePoint; // 포인트
    const totalAmont = orderStatusData.data.totalAmont; // 총 결제금액
    const orderRequest = orderStatusData.data.orderRequest; // 세탁 요청사항

    
    /**
     * 전체 진행상태
     */
    const statusSummaryEl = document.querySelector('.status-summary');
    statusSummaryEl.textContent = status;

    /**
     * 진행상태, 결제금액 탭
     */
    const s_laundryCountNum = document.querySelector('.c_status .laundry-count-num');
    const p_laundryCountNum = document.querySelector('.c_payment .laundry-count-num');
    const statusList = document.querySelector('.c_status .laundry-container');
    const paymentList = document.querySelector('.c_payment .laundry-container');

    s_laundryCountNum.textContent = laundryResponseDtoList.length;
    p_laundryCountNum.textContent = laundryResponseDtoList.length;

    for (let i = 0; i < laundryResponseDtoList.length; i++) {
      statusList.insertAdjacentHTML('beforeend', 
      `
      <div class="laundry-wrap">
        <div class="laundry-info">
          <img src="${laundryResponseDtoList[i].imageUrl}" alt="세탁물 사진">
          <div class="laundry-info-detail">
            <p class="laundry-type">${laundryResponseDtoList[i].clothesType}</p>
            <p class="laundry-num">01452${laundryResponseDtoList[i].laundryId}</p>
          </div>
        </div>
        <div class="laundry-status">${laundryResponseDtoList[i].status}</div>  
      </div>
      `)
    }

    for (let i = 0; i < laundryResponseDtoList.length; i++) {
      paymentList.insertAdjacentHTML('beforeend', 
      `
      <div class="laundry-wrap">
        <div class="laundry-info">
          <img src="${laundryResponseDtoList[i].imageUrl}" alt="세탁물 사진">
          <div class="laundry-info-detail">
            <p class="laundry-type">${laundryResponseDtoList[i].clothesType}</p>
            <p class="laundry-num">01452${laundryResponseDtoList[i].laundryId}</p>
          </div>
        </div>
        <div class="laundry-price"><span>${laundryResponseDtoList[i].stablePrice.toLocaleString('ko-KR')}</span>원</div>
      </div>
      `)
    }

    /**
     * 총 결제 금액
     */

    // 안심정찰가격
    const totalOrderPriceEl = document.querySelector('.total-order-price');
    totalOrderPriceEl.textContent = totalStablePrice.toLocaleString('ko-KR');

    // 수거배송비
    const deliveryPriceEl = document.querySelector('.delivery-price');
    deliveryPriceEl.textContent = deliveryFee.toLocaleString('ko-KR');

    // 추가요금
    const extraFeeEl = document.querySelector('.extra-fee');
    extraFeeEl.textContent = surcharge.toLocaleString('ko-KR');

    // 포인트할인
    const pointDiscountPriceEl = document.querySelector('.point-discount-price');
    pointDiscountPriceEl.textContent = usePoint.toLocaleString('ko-KR');

    // 총 결제 금액
    const priceSumDetailEl = document.querySelector('.price-sum .price-sum-detail');
    priceSumDetailEl.textContent = totalAmont.toLocaleString('ko-KR');

    // 세탁 요청사항
    const requestFieldEl = document.querySelector('.request-field p');
    requestFieldEl.textContent = orderRequest;

  } catch (error) {
    console.error('주문 상태 조회에 실패했습니다:', error);
  }
}



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