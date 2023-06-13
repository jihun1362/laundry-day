/**
 * 스크롤 이벤트
 */
// 스크롤 수평 이동 
gsap.to(".service_comment", {
  scrollTrigger: {
    trigger: '.service_comment', // 대상
    start: "-50 center", //시작 지점
    end: "+=400", //시작 부분부터 500px까지 스크롤 한 후 종료
    scrub: 1, //부드러운 스크러빙
    // markers: true, //개발 가이드 선
  },
  x: -850,
});

// 페이지 스크롤에 영향을 받는 요소들을 검색
const socialBarEl = document.querySelector('.social_bar');
// 페이지에 스크롤 이벤트를 추가
// 스크롤이 지나치게 자주 발생하는 것을 조절(throttle, 일부러 부하를 줌)
window.addEventListener('scroll', _.throttle(function () {
  // 페이지 스크롤 위치가 3600px이 넘으면.
  if (window.scrollY > 3600) {
    // 상단으로 스크롤 버튼 숨기기!
    gsap.to(socialBarEl, .2, {
      x: 100
    });
  // 페이지 스크롤 위치가 3600px이 넘지 않으면.
  } else {
    // 상단으로 스크롤 버튼 보이기!
    gsap.to(socialBarEl, .2, {
      x: 0
    });
  }
}, 300));  
// _. throttle(함수, 시간)