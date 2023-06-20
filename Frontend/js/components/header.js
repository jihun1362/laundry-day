// 로그인 시 토큰을 쿠키에서 가져오는 함수
function getCookie(name) {
  const cookieValue = document.cookie.match('(^|;)\\s*' + name + '\\s*=\\s*([^;]+)');
  return cookieValue ? cookieValue.pop() : '';
}

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
          <li><a href="javascript:void(0)" @click="openModal">세탁신청</a></li>
        </ul>
        <div class="menu_list" :class="{ on: isMenuVisible }">
          <ul>
            <li><a href="#" class="text">OUR SERVICES<span class="material-symbols-outlined">navigate_next</span></a></li>
            <li><a href="#">이용방법<span class="material-symbols-outlined">navigate_next</span></a></li>
            <li><a href="#">가격표<span class="material-symbols-outlined">navigate_next</span></a></li>
            <li><a href="javascript:void(0)" @click="openModal">세탁신청<span class="material-symbols-outlined">navigate_next</span></a></li>
            <li class="util">
              <ul>
                <li><iconify-icon icon="material-symbols:help-outline"></iconify-icon><a href="#">공지사항</a> | <a href="#">자주 찾는 질문</a></li>
                <li>
                  <iconify-icon icon="mdi:account"></iconify-icon>
                  <a v-if="isLoggedIn" href="/Frontend/views/mypage.html">내 계정</a>
                  <a v-else href="/Frontend/views/login.html">로그인</a>
                  <a v-if="isLoggedIn" href="javascript:void(0)" class="show-logout" @click="logout">로그아웃</a>
                </li>
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
          <li class="user">
            <a v-if="isLoggedIn" href="/Frontend/views/mypage.html">마이페이지<iconify-icon icon="mdi:account"></iconify-icon></a>
            <a v-else href="/Frontend/views/login.html">계정<iconify-icon icon="mdi:account"></iconify-icon></a>
          </li>
          <li class="bell"><a href="#">알림<iconify-icon icon="ph:bell-bold"></iconify-icon></a></li>
          <li class="list"><a href="/Frontend/views/order-status.html">이용내역<iconify-icon icon="ci:shopping-bag-02"></iconify-icon></a></li>
          <li class="help"><a href="#">고객지원<iconify-icon icon="material-symbols:help-outline"></iconify-icon></a></li>
        </ul>
      </nav>
    </div>
    <!-- 모달 -->
    <div v-if="isModalVisible" class="modal">
      <div class="modal-content">
        <h3>꼭 확인해주세요!</h3>
        <p>
          <span> 버려도 되는 일회용 백</span>에 담아<br>
          <span>런드리데이 + 세탁물 명</span>을<br>적어주세요
        </p>
      </div>
      <div class="example-img"></div>
      <div class="close-wrap">
        <a href="javascript:void(0)" class="today-modal-close-btn" @click="hideModal">오늘 하루 보지 않음</a>
        <a href="javascript:void(0)" class="modal-close-btn" @click="hideModal">
          <span>Close<iconify-icon icon="heroicons:x-mark-solid"></iconify-icon></span>
        </a>
      </div>
    </div>

  </header>
  `,
  data() {
    return {
      isMenuVisible: false,
      isModalVisible: false,
      isLoggedIn: false
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
    },
    openModal() {
      // 세탁신청 페이지로 이동
      window.location.href = '/Frontend/views/laundry-request.html';
    },
    hideModal() {
      // 오늘 하루 보지 않음을 선택한 경우 쿠키를 생성하여 상태 저장
      const expirationDate = new Date();
      expirationDate.setDate(expirationDate.getDate() + 1); // 다음 날로 설정
      document.cookie = `hideModal=true; expires=${expirationDate.toUTCString()}; path=/`;
      
      this.isModalVisible = false;
    },
    shouldShowModal() {
      // 쿠키에서 hideModal 상태를 확인하여 모달을 표시할지 여부를 결정
      const cookies = document.cookie.split(';');
      for (let i = 0; i < cookies.length; i++) {
        const cookie = cookies[i].trim();
        if (cookie.startsWith('hideModal=true')) {
          return false; // 오늘 하루 보지 않음 선택한 경우 모달 표시 안 함
        }
      }
      return true; // 모달을 표시해야 함
    },
    logout() {
      // 쿠키에서 토큰 제거
      document.cookie = 'token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
      // 로그아웃 후 리다이렉트 또는 필요한 동작 수행
      window.location.href = '/Frontend/views/login.html'; // 로그인 페이지로 리다이렉트
    }
  },
  mounted() {
    window.addEventListener('resize', this.checkSize);
    // URL에 "laundry-request.html"이 포함되어 있는 경우 모달을 표시
    if (window.location.href.includes('laundry-request.html') && this.shouldShowModal()) {
      this.isModalVisible = true;
    }
    // 쿠키에서 토큰 확인하여 로그인 상태 업데이트
    const token = getCookie('token');
    if (token) {
      this.isLoggedIn = true;
    }
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.checkSize);
  }
});
