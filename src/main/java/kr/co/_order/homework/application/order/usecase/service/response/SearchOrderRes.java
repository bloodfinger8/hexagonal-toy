package kr.co._order.homework.application.order.usecase.service.response;

import kr.co._order.homework.application.order.domain.OrderProduct;
import kr.co._order.homework.application.order.domain.Orders;
import kr.co._order.homework.application.order.domain.type.OrderStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SearchOrderRes {
    private List<OrderHistory> orderHistories;

    private SearchOrderRes(List<OrderHistory> orderHistories) {
        this.orderHistories = orderHistories;
    }

    public static SearchOrderRes from(List<Orders> orders){
        return new SearchOrderRes(orders.stream()
                .map(order -> OrderHistory.of(order.getOrderId(), order.getOrderStatus(), order.getCreatedAt(), order.getOrderProducts()))
                .collect(Collectors.toList()));
    }

    public List<OrderHistory> getOrderHistories() {
        return orderHistories;
    }

    public static class OrderHistory {
        private String orderId;
        private OrderStatus orderStatus;
        private ZonedDateTime createdAt;
        private List<OrderProductsHistory> orderProducts;

        private OrderHistory(String orderId, OrderStatus orderStatus, ZonedDateTime createdAt, List<OrderProductsHistory> orderProducts) {
            this.orderId = orderId;
            this.orderStatus = orderStatus;
            this.createdAt = createdAt;
            this.orderProducts = orderProducts;
        }
        private static OrderHistory of(String orderId, OrderStatus orderStatus, ZonedDateTime createdAt, List<OrderProduct> orderProducts) {
            return new OrderHistory(orderId,orderStatus,createdAt,
                    orderProducts.stream()
                            .map(orderProduct -> new OrderProductsHistory(orderProduct.getProductInfo().getProductName(),orderProduct.getQuantity()))
                            .collect(Collectors.toList())
            );
        }

        public String getOrderId() {
            return orderId;
        }

        public OrderStatus getOrderStatus() {
            return orderStatus;
        }

        public ZonedDateTime getCreatedAt() {
            return createdAt;
        }

        public List<OrderProductsHistory> getOrderProducts() {
            return orderProducts;
        }

        public static class OrderProductsHistory {
            private String productName;
            private int quantity;

            public OrderProductsHistory(String productName, int quantity) {
                this.productName = productName;
                this.quantity = quantity;
            }

            public String getProductName() {
                return productName;
            }

            public int getQuantity() {
                return quantity;
            }
        }
    }
}
