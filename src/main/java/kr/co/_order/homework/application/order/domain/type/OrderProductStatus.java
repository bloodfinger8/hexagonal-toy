package kr.co._order.homework.application.order.domain.type;

public enum OrderProductStatus {
    INIT{
        @Override
        public String description() { return "결제 대기 중"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == PAYMENT;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.INIT;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    },
    PAYMENT{
        @Override
        public String description() { return "발주 대기 중"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == PAYMENT_CANCEL || next == PRODUCT_CANCEL || next == PRODUCT_PREPARING;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.PAYMENT_COMPLETE;
        }

        @Override
        public boolean isPurchaseCancel() {
            return true;
        }
        @Override
        public boolean isExchange() {
            return false;
        }
        @Override
        public boolean isRefund() {
            return false;
        }
        @Override
        public boolean isPurchaseConfirm() {
            return false;
        }
    },
    PAYMENT_CANCEL{
        @Override
        public String description() { return "결제 취소"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return false;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.REFUND_COMPLETE;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    },
    PRODUCT_PREPARING{
        @Override
        public String description() { return "상품 제작 중"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == DELIVERING || next == PRODUCT_CANCEL;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.PAYMENT_COMPLETE;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    },
    DELIVERING{
        @Override
        public String description() { return "배송 중"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == DELIVERY_COMPLETE;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.SHIPPING;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    },
    DELIVERY_COMPLETE {
        @Override
        public String description() { return "배송 완료"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == PURCHASE_CONFIRM || next == CHANGE_REQUEST || next == REFUND_REQUEST;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.DELIVERY_COMPLETE;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return true;}
        @Override
        public boolean isRefund() {return true;}
        @Override
        public boolean isPurchaseConfirm() {return true;}
    },
    CHANGE_DELIVERING{
        @Override
        public String description() { return "교환 배송 중"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == CHANGE_DELIVERING_COMPLETE;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.EXCHANGE;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    },
    CHANGE_DELIVERING_COMPLETE{
        @Override
        public String description() { return "교환 배송 완료"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == PURCHASE_CONFIRM || next == REFUND_REQUEST;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.EXCHANGE_COMPLETE;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return true;}
        @Override
        public boolean isPurchaseConfirm() {return true;}
    },
    PURCHASE_CONFIRM{
        @Override
        public String description() { return "구매 확정"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return false;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.PURCHASE_CONFIRMATION;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    },
    CHANGE_REQUEST{
        @Override
        public String description() { return "교환 요청"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == CHANGE_CONFIRM || next == CHANGE_REJECT;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.EXCHANGE;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    },
    CHANGE_CONFIRM{
        @Override
        public String description() { return "교환 준비 중"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == CHANGE_DELIVERING || next == PRODUCT_CANCEL;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.EXCHANGE;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    },
    CHANGE_REJECT{
        @Override
        public String description() { return "교환 거절"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == REFUND_REQUEST || next == PURCHASE_CONFIRM;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.EXCHANGE;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return true;}
        @Override
        public boolean isPurchaseConfirm() {return true;}
    },
    REFUND_REQUEST{
        @Override
        public String description() { return "환불 요청"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == REFUND_CONFIRM || next == REFUND_REJECT;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.REFUND;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    },
    REFUND_CONFIRM{
        @Override
        public String description() { return "환불 완료"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return false;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.REFUND_COMPLETE;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    },
    REFUND_REJECT{
        @Override
        public String description() { return "환불 거절"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return next == CHANGE_REQUEST || next == PURCHASE_CONFIRM;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.REFUND;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return true;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return true;}
    },
    PRODUCT_CANCEL{
        @Override
        public String description() { return "제작 취소"; }
        @Override
        public boolean canNext(OrderProductStatus next) {
            return false;
        }
        @Override
        public OrderProductGroup getOrderProductGroup() {
            return OrderProductGroup.REFUND_COMPLETE;
        }

        @Override
        public boolean isPurchaseCancel() {return false;}
        @Override
        public boolean isExchange() {return false;}
        @Override
        public boolean isRefund() {return false;}
        @Override
        public boolean isPurchaseConfirm() {return false;}
    };

    public abstract String description();
    public abstract boolean canNext(OrderProductStatus next);
    public abstract OrderProductGroup getOrderProductGroup();
    public abstract boolean isPurchaseCancel();
    public abstract boolean isExchange();
    public abstract boolean isRefund();
    public abstract boolean isPurchaseConfirm();


//    public static List<OrderProductStatus> getOrderProductStatus(OrderProductGroup orderProductGroup){
//
//        if(orderProductGroup.equals(OrderProductGroup.ALL_DELIVERY) || orderProductGroup.equals(OrderProductGroup.ALL_EXCHANGE_REFUND)) {
//            return EnumSet.allOf(OrderProductStatus.class)
//                    .stream()
//                    .filter(t -> t.getOrderProductGroup().getGroup().equals(orderProductGroup.getGroup()))
//                    .collect(Collectors.toList());
//        }
//
//        return EnumSet.allOf(OrderProductStatus.class)
//                .stream()
//                .filter(type -> type.getOrderProductGroup().equals(orderProductGroup))
//                .collect(Collectors.toList());
//    }
}
