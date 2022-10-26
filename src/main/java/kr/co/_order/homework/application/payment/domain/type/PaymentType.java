package kr.co._order.homework.application.payment.domain.type;

public enum PaymentType {
    CARD {
        @Override
        public String typeName() {
            return "신용카드";
        }
    },
    MONEY {
        @Override
        public String typeName() {
            return "신용카드";
        }
    },
    OTHER {
        @Override
        public String typeName() { return "기타"; }
    };

    public abstract String typeName();
}
