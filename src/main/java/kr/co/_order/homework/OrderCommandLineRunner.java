package kr.co._order.homework;

import kr.co._order.homework.application.order.usecase.port.in.GenerateOrderUseCase;
import kr.co._order.homework.application.order.usecase.port.in.SearchOrderUseCase;
import kr.co._order.homework.application.order.usecase.service.response.GenerateOrderRes;
import kr.co._order.homework.application.order.usecase.service.response.SearchOrderRes;
import kr.co._order.homework.application.product.domain.exception.SoldOutException;
import kr.co._order.homework.application.product.usecase.port.in.SearchAllProductUseCase;
import kr.co._order.homework.application.product.usecase.service.response.SearchAllProductRes;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Profile("!test")
@Component
public class OrderCommandLineRunner implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final SearchAllProductUseCase searchAllProductUseCase;
    private final GenerateOrderUseCase generateOrderUseCase;
    private final SearchOrderUseCase searchOrderUseCase;

    public OrderCommandLineRunner(SearchAllProductUseCase searchAllProductUseCase, GenerateOrderUseCase generateOrderUseCase, SearchOrderUseCase searchOrderUseCase) {
        this.searchAllProductUseCase = searchAllProductUseCase;
        this.generateOrderUseCase = generateOrderUseCase;
        this.searchOrderUseCase = searchOrderUseCase;
    }

    @Override
    public void run(String... args) {
        System.out.print("입력(o[order]: 주문, q[quit]: 종료, h(history): 주문 내역) : ");

        while (true) {
            String input = scanner.nextLine();

            if(input.equals("o")) {
                printProduct();

                Map<String , Integer> orderProducts = new HashMap<>();
                while (true) {
                    String OrderId = inputOrderProductId();
                    String OrderQuantity = inputOrderQuantity();

                    if(Strings.isBlank(OrderId) || Strings.isBlank(OrderQuantity)) {
                        printOrderHistory(orderProducts);
                        return;
                    }
                    orderProducts.put(OrderId, Integer.parseInt(OrderQuantity));
                }
            }

            if(input.equals("h")) {
                printOrderHistory();
                run();
            }


            if(input.equals("q") || input.equals("quit")) {
                return;
            }
        }
    }





    private void printOrderHistory() {
        SearchOrderRes searchOrderRes = searchOrderUseCase.searchOrder();
        System.out.println("주문번호             주문상태             주문일자");
        for(SearchOrderRes.OrderHistory orderHistory : searchOrderRes.getOrderHistories()) {
            System.out.println(orderHistory.getOrderId() + "    " + orderHistory.getOrderStatus() + "   " + orderHistory.getCreatedAt());
            for (SearchOrderRes.OrderHistory.OrderProductsHistory productsHistory: orderHistory.getOrderProducts()){
                System.out.println(">> 주문상품명 : " + productsHistory.getProductName() + "    주문수량 : " + productsHistory.getQuantity());
            }
            System.out.println("-----------------------------------------------------");
        }
    }

    private String inputOrderQuantity() {
        System.out.print("수량 : ");
        return this.scanner.nextLine();
    }

    private String inputOrderProductId() {
        System.out.println();
        System.out.print("상품번호 : ");
        return this.scanner.nextLine();
    }

    private void printOrderHistory(Map<String, Integer> map) {
        GenerateOrderRes makeOrderRes = new GenerateOrderRes();
        try {
            makeOrderRes = generateOrderUseCase.generateOrder(map);
            System.out.println("주문내역:");
            System.out.println("----------------------------------");
            for(String history : makeOrderRes.getPaymentHistory()){
                System.out.println(history);
            }
            System.out.println("----------------------------------");
            System.out.println("주문금액: " + makeOrderRes.getOrderPrice());
            System.out.println("배송금액: " + makeOrderRes.getDeliveryFee());
            System.out.println("----------------------------------");
            System.out.println("지불금액: " + makeOrderRes.getTotalPrice());
            System.out.println("----------------------------------");
        }catch (SoldOutException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        run();
    }

    private void printProduct() {
        System.out.println("상품번호    상풍명                 판매가격        재고수");
        for (SearchAllProductRes.ProductData res: searchAllProductUseCase.searchAllProducts().getProductData()) {
            System.out.println(res.toString());
        }
    }
}
