Vue.component('app-footer', {
  template: `
    <footer>
      <div id="to-top" @click="scrollToTop"><span class="material-symbols-outlined">arrow_upward</span></div>
      <div class="footer_top">
        <p class="footer_typo">
          저녁 있는 삶을 위해<br>세탁에서 퇴근하세요
          <span class="footer_typo-blank"></span>
        </p>
        <nav class="footer_sns-wrap">
          <ul class="footer_sns">
            <li><a href="javascript:void(0)">페이스북</a></li>
            <li><a href="javascript:void(0)">구글+</a></li>
            <li><a href="javascript:void(0)">유튜브</a></li>
            <li><a href="javascript:void(0)">인스타그램</a></li>
          </ul>
        </nav>
      </div>
      <div class="footer_bottom">
        <div class="footer_info">
          <p class="copyright">LAUNDRY DAY &copy; <span class="this-year"></span></p>
        </div>
        <div class="footer_info-detail">
          <p>
            <span>TEL : 02-3704-5000</span>
            <span>FAX : 02-1234-5678</span>
            <span>hello@laundryday.com</span>
          </p>
          <p>
            <span>서울특별시 종로구 종로33길 15, 연강빌딩</span>
            <span>사업자등록번호 012-34-56789</span>
          </p>
        </div>
        <nav class="footer_menu-wrap">
          <ul class="footer_menu">
            <li class="private"><a href="javascript:void(0)">개인정보처리방침</a></li>
          </ul>
        </nav>
      </div>
    </footer>
  `,
  mounted() {
    const thisYear = document.querySelector('.this-year');
    thisYear.textContent = new Date().getFullYear();
  },
  methods: {
    scrollToTop() {
      gsap.to(window, 0.7, {
        scrollTo: 0
      });
    }
  }
});