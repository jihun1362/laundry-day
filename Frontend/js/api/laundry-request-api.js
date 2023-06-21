/**
 * 토큰 가져오기
 */
let cookies = document.cookie.split(';');
let myToken = '';

for (let i = 0; i < cookies.length; i++) {
  let cookie = cookies[i].trim();
  if (cookie.startsWith('token=')) {
    myToken = cookie.substring('token='.length, cookie.length);
    break;
  }
}


/**
 * 주소 조회
 */
export async function getAddress() {
  try {
    // GET 요청
    const res = await fetch('http://3.35.18.15:8080/api/address', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${myToken}`
      }
    });

    // 응답이 정상적이지 않을 경우
    if (!res.ok) {
      throw new Error('네트워크 응답이 정상적이지 않습니다.');
    }

    // 응답 데이터를 JSON 형식으로 파싱
    const addressData = await res.json();
    return addressData;
  } catch (error) {
    console.error('Fetch 작업에 문제가 발생했습니다:', error);
    throw error;
  }
}

/**
 * 주소 등록
 */
export async function saveAddress(addressData) {
  try {
    // POST 요청
    const res = await fetch('http://3.35.18.15:8080/api/address', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${myToken}`
      },
      body: JSON.stringify(addressData)
    });

    // 응답이 정상적이지 않을 경우
    if (!res.ok) {
      throw new Error('네트워크 응답이 정상적이지 않습니다.');
    }

    // 응답 데이터를 JSON 형식으로 파싱
    const responseData = await res.json();
    return responseData;
  } catch (error) {
    console.error('Fetch 작업에 문제가 발생했습니다:', error);
    throw error;
  }
}

