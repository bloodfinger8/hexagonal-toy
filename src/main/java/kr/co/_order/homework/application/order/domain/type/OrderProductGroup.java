package kr.co._order.homework.application.order.domain.type;

public enum OrderProductGroup {
    INIT("0" , "결제 전")

    ,ALL_DELIVERY("1" , "전체")
    ,PAYMENT_COMPLETE("1","입금/결제")
    ,SHIPPING("1", "배송중")
    ,DELIVERY_COMPLETE("1" , "배송완료")
    ,PURCHASE_CONFIRMATION("1", "구매확정")

    ,ALL_EXCHANGE_REFUND("2" , "전체")
    ,EXCHANGE ("2","교환")
    ,EXCHANGE_COMPLETE ("2" , "교환완료")
    ,REFUND("2" ,"환불")
    ,REFUND_COMPLETE("2" , "환불완료");

    private final String group;
    private final String comment;

    OrderProductGroup(String group, String comment) {
        this.group = group;
        this.comment = comment;
    }
}
