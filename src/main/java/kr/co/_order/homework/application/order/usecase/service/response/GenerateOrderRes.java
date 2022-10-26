package kr.co._order.homework.application.order.usecase.service.response;

import kr.co._order.homework.application.payment.domain.PaymentAllow;

import java.util.List;

public class GenerateOrderRes {
    private List<String> paymentHistory;
    private long orderPrice;
    private long deliveryFee;
    private long totalPrice;

    public GenerateOrderRes(){}

    private GenerateOrderRes(List<String> paymentHistory, long orderPrice, long deliveryFee, long totalPrice) {
        this.paymentHistory = paymentHistory;
        this.orderPrice = orderPrice;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
    }

    public static GenerateOrderRes of(List<String> paymentHistory, long orderPrice, PaymentAllow paymentAllow){
        return new GenerateOrderRes(paymentHistory,orderPrice,paymentAllow.getDeliveryFee(),paymentAllow.getTotalOrderPrice());
    }

    public List<String> getPaymentHistory() {
        return paymentHistory;
    }

    public long getOrderPrice() {
        return orderPrice;
    }

    public long getDeliveryFee() {
        return deliveryFee;
    }

    public long getTotalPrice() {
        return totalPrice;
    }
}
