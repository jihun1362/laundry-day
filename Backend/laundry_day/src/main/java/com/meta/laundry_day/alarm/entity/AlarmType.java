package com.meta.laundry_day.alarm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    OrderComplete("세탁주문성공", "세탁 주문을 요청하셨습니다. 세탁물 수거는 일반 세탁의 경우 매일 밤 10시, 당일 세탁의 경우 매일 오전 10시에 수거가 시작됩니다. 더 자세한 사항이 궁금하시면 고객센터로 문의바랍니다."),
    PickupStart("수거시작", "수거가 시작됩니다. 원활한 세탁물을 수거를 위해 문앞에 세탁물을 나둬주시기 바랍니다. 더 자세한 사항이 궁금하시면 고객센터로 문의바랍니다."),
    WashingStart("세탁시작", "세탁이 시작됩니다. 결제금액을 확인하시려면 진행 상태를 확인해주세요. 세탁 완료 후 결제가 진행될 예정이오니 참고 바랍니다. 더 자세한 사항이 궁금하시면 고객센터로 문의바랍니다."),
    DeliveryStart("배송시작", "배송이 시작됩니다. 고객님의 세탁물이 기재된 주소로 배송됩니다. 더 자세한 사항이 궁금하시면 고객센터로 문의바랍니다."),
    CompletePayment("결제완료", "결제가 완료 되었습니다. 결제 영수증 및 자세한 사항은 결제 내역을 통해 확인 하실 수 있습니다. 더 자세한 사항이 궁금하시면 고객센터로 문의바랍니다."),
    DeliveryDone("배송완료", "배송이 완료되었니다. 고객님의 세탁물이 기재된 주소로 배송이 완료 되었습니다. 더 자세한 사항이 궁금하시면 고객센터로 문의바랍니다.");

    private final String type;
    private final String content;
}
