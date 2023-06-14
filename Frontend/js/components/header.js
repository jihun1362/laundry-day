Vue.component('app-header', {
  template: `
  <header>
    <div class="header_top">15, JONG-RO 33-GIL, JONGNO-GU, SEOUL | TEL- 02-3704-5000</div>
    <div class="header_bottom">
      <h1 class="logo"><a href="/Frontend/index.html">○LAUNDRY○DAY○</a></h1>
      <nav class="category">
        <a href="#" class="menu_btn" @click="showMenu">
          <!-- ::before -->
          <span>Menu</span>
          <!-- ::after -->
        </a>
        <ul>
          <li><a href="#" class="text">OUR SERVICES</a></li>
          <li><a href="#">이용방법</a></li>
          <li><a href="#">가격표</a></li>
          <li><a href="#">세탁신청</a></li>
        </ul>
        <div class="menu_list" :class="{ on: isMenuVisible }">
          <ul>
            <li><a href="#" class="text">OUR SERVICES<span class="material-symbols-outlined">navigate_next</span></a></li>
            <li><a href="#">이용방법<span class="material-symbols-outlined">navigate_next</span></a></li>
            <li><a href="#">가격표<span class="material-symbols-outlined">navigate_next</span></a></li>
            <li><a href="#">세탁신청<span class="material-symbols-outlined">navigate_next</span></a></li>
            <li class="util">
              <ul>
                <li><iconify-icon icon="material-symbols:help-outline"></iconify-icon><a href="#">공지사항</a> | <a href="#">자주 찾는 질문</a></li>
                <li><iconify-icon icon="mdi:account"></iconify-icon><a href="/Frontend/views/login.html">로그인</a></li>
              </ul>
            </li>
          </ul>
          <a href="#" class="close_btn" @click="hideMenu">
            <span>Close<iconify-icon icon="heroicons:x-mark-solid" style="color: #626262;"></iconify-icon></span>
          </a>
        </div>
      </nav>
      <nav class="util">
        <ul>
          <li class="user"><a href="/Frontend/views/login.html">계정<iconify-icon icon="mdi:account"></iconify-icon></a></li>
          <li class="bell"><a href="#">알림<iconify-icon icon="ph:bell-bold"></iconify-icon></a></li>
          <li class="list"><a href="#">이용내역<iconify-icon icon="ci:shopping-bag-02"></iconify-icon></a></li>
          <li class="help"><a href="#">고객지원<iconify-icon icon="material-symbols:help-outline"></iconify-icon></a></li>
        </ul>
      </nav>
    </div>
  </header>
  `,
  data() {
    return {
      isMenuVisible: false
    };
  },
  methods: {
    showMenu() {
      this.isMenuVisible = true;
      // 스크롤 제거
      document.body.style.overflow = 'hidden';
      // 현재 페이지에서 스크롤을 내린 만큼 값을 저장
      const scrollPosition = window.pageYOffset;
      document.body.style.top = `-${scrollPosition}px`;
    },
    hideMenu() {
      this.isMenuVisible = false;
      // 스크롤 복원
      const scrollPosition = parseInt(document.body.style.top.replace('-', ''));
      document.body.style.overflow = 'visible';
      window.scrollTo(0, scrollPosition);
    },
    checkSize() {
      const size = window.innerWidth;
      if (size >= 990) {
        this.isMenuVisible = false;
      }
    }
  },
  mounted() {
    window.addEventListener('resize', this.checkSize);
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.checkSize);
  }
});
