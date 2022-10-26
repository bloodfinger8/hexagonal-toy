package kr.co._order.homework.application.order.domain.type;

public enum OrderStatus {
    INIT {
        @Override
        public String description() { return "결제 대기"; }
        @Override
        public boolean canNext(OrderStatus next) { return next == PAYMENT || next == PAYMENT_FAIL; }
    },
    PAYMENT {
        @Override
        public String description() { return "결제 완료"; }
        @Override
        public boolean canNext(OrderStatus next) { return next == PARTIAL_CANCELLATION || next == COMPLETE; }
    },
    PAYMENT_FAIL {
        @Override
        public String description() { return "결제 실패"; }
        @Override
        public boolean canNext(OrderStatus next) { return false; }
    },
    PARTIAL_CANCELLATION {
        @Override
        public String description() { return "부분 취소"; }
        @Override
        public boolean canNext(OrderStatus next) { return next == COMPLETE; }
    },
    COMPLETE {
        @Override
        public String description() { return "처리 완료"; }
        @Override
        public boolean canNext(OrderStatus next) { return false; }
    };

    public abstract String description();
    public abstract boolean canNext(OrderStatus next);
}
