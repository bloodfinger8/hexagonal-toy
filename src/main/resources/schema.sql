drop table if exists ORDERS;
drop table if exists ORDER_PRODUCT;
drop table if exists PRODUCT;
drop table if exists PAYMENT_ALLOW;


create table orders (
id bigint generated by default as identity,
order_id varchar(40) not null comment '주문 번호',
order_status varchar(40) not null comment '주문 상태' ,
transacted_at timestamp,
created_at timestamp comment '생성시점',
updated_at timestamp comment '변경시점',
primary key (id)
);

create table order_product (
id                              bigint generated by default as identity,
order_id                        varchar(40) not null comment '주문 번호',
order_product_id                varchar(40) not null comment '주문 상품 번호',
product_id                      varchar(40) not null comment '주문 당시 상품 번호',
product_name                    varchar(90) comment '주문 당시 상품 이름',
product_thumbnail_url           varchar(1024) comment '주문 상품 이미지 url',
ordered_total_price             bigint not null comment '주문 상품 가격 (상품 * 갯수)',
ordered_product_price           bigint not null comment '주문 상품 가격',
ordered_original_product_price  bigint not null comment '주문 상품 원가격',
order_product_status            varchar(40) not null comment '주문 상품 상태(결제/배송/완료...)',
quantity                        integer not null comment '주문 상품 갯수',
auto_confirm_reserve_datetime   timestamp comment '구매확정 마감일자',
created_at                      timestamp comment '생성 일자',
updated_at                      timestamp comment '변경 일자',
confirmed_at                    timestamp comment '구매확정 일자',
primary key (id)
);

create table payment_allow (
id                      bigint generated by default as identity,
order_id                varchar(40) not null comment '주문 번호',
delivery_fee            bigint not null comment '배송 금액',
discount                bigint not null comment '할인 금액',
order_price             bigint not null comment '주문 금액',
total_order_price       bigint not null comment '주문 금액 + 배송비',
approved_at             timestamp comment '결제 승인 일자',
created_at              timestamp comment '생성 일자',
payment_method_type     varchar(10) not null comment '결제 방법',
primary key (id)
);

create table product (
id bigint               generated by default as identity,
product_id              varchar(90) not null,
product_name            varchar(90) not null,
price                   integer not null,
quantity                integer not null,
primary key (id)
);

alter table order_product add constraint UK_mu87al1d2kl1swnotmlbrsn7h unique (order_product_id);

alter table orders add constraint UK_hmsk25beh6atojvle1xuymjj0 unique (order_id);

alter table payment_allow add constraint UK_5adgp2s807gx2at02gm0p7xot unique (order_id);