package kr.co._order.homework.application.order.usecase.service;

import kr.co._order.homework.application.order.usecase.port.in.GenerateOrderUseCase;
import kr.co._order.homework.application.order.usecase.service.response.GenerateOrderRes;
import kr.co._order.homework.application.payment.domain.Amount;
import kr.co._order.homework.application.product.domain.exception.SoldOutException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class MakeOrderServiceImplTest {
    Map<String, Integer> multiThreadData  = new HashMap<>();
    Map<String, Integer> dataWithDeliveryFee  = new HashMap<>();
    Map<String, Integer> dataWithoutDeliveryFee  = new HashMap<>();

    @Autowired
    GenerateOrderUseCase generateOrderUseCase;

    @BeforeEach
    void setUp() {
        multiThreadData  = Map.of("768848", 10);
        dataWithDeliveryFee  = Map.of("628066", 1);
        dataWithoutDeliveryFee  = Map.of("377169", 40, "760709", 10);
    }

    @Test
    @DisplayName("multi thread 요청으로 SoldOutException 동작 테스트")
    void multiThreadEnvironmentExceptionTest() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger();
        int executeCount = 5;
        ExecutorService service = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(executeCount);

        for (int i = 0; i < executeCount; i++) {
            service.execute(() -> {
                try {
                    GenerateOrderRes makeOrderRes = generateOrderUseCase.generateOrder(multiThreadData);
                    successCount.getAndIncrement();
                    System.out.println(Thread.currentThread() + " 성공 - 주문금액 : " + makeOrderRes.getOrderPrice());
                } catch (SoldOutException e) {
                    System.out.println(e.getMessage());
                } catch (ObjectOptimisticLockingFailureException e) {
                    System.out.println("transaction crash");
                    generateOrderUseCase.generateOrder(multiThreadData);
                }
                latch.countDown();
            });
        }
        latch.await();

        assertThat(successCount.get()).isEqualTo(4);
    }

    @Test
    @DisplayName("주문 금액 계산(배송비 미포함)")
    void orderProductPriceTest() {
        GenerateOrderRes makeOrderRes = generateOrderUseCase.generateOrder(dataWithoutDeliveryFee);

        long expectOrderPrice = 998000L;
        assertThat(makeOrderRes.getOrderPrice()).isEqualTo(expectOrderPrice);
        assertThat(makeOrderRes.getDeliveryFee()).isEqualTo(Amount.deliveryEvent(expectOrderPrice));
        assertThat(makeOrderRes.getTotalPrice()).isEqualTo(expectOrderPrice+Amount.deliveryEvent(expectOrderPrice));
    }

    @Test
    @DisplayName("주문 금액 계산(배송비 포함)")
    void orderProductPriceWithDeliveryFeeTest() {
        GenerateOrderRes makeOrderRes = generateOrderUseCase.generateOrder(dataWithDeliveryFee);

        long expectOrderPrice = 12900L;
        assertThat(makeOrderRes.getOrderPrice()).isEqualTo(expectOrderPrice);
        assertThat(makeOrderRes.getDeliveryFee()).isEqualTo(Amount.deliveryEvent(expectOrderPrice));
        assertThat(makeOrderRes.getTotalPrice()).isEqualTo(expectOrderPrice + Amount.deliveryEvent(expectOrderPrice));
    }
}