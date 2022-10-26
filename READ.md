
## 요구사항 분석 및 추가 리스트
- 상품은 고유의 상품번호 , 상품명 , 판매가격 , 재고수량
- 한 번에 여러개의 상품주문 가능
- 상품 번호 , 주문 수량은 반복적 입력 ( 똑같은 상품을 주문할 수 있다.)
- 주문은 상품번호 , 수량을 입력
    - 재고 부족시 SoldOutException 예외처리
    - 상품번호 잘못 입력시 IllegalArgumentException 예외처리
    - 금액은 제한 없음
    - space + enter 입력시 한건의 주문 완료 및 또 다른 주문가능
    - 주문 상품번호와 수량은 Map을 통해 입력
- 주문 금액이 5만원 미만인 경우 배송료 2500원 추가
- 주문 완료시 주문 내역 , 주문 총 금액 , 결제 금액(배송비 포함) 출력
- 'h' 주문 내역 및 상세정보 확인 가능
- ‘q’ 또는 quit 입력시 프로그램 종료
- 상품 데이터는 주어짐
    - in memory db (h2) 라이브러리 이용 
    - 주어진 상품 데이터 사용
- 테스트는 반드시 멀티쓰레드 요청으로 SoldOutException 이 정상 동작하는지 확인 하는 단위 테스트가 작성되어야 합니다.
  - 비관적 락 이용한 트랜잭션 관리
---
## 구동 방법 및 테스트 코드 실행
in memory 설치 -> https://www.h2database.com/html/main.html

```shell
# h2 DB 실행
cd ~/h2
cd /bin
./h2.sh
```
콘솔 실행
> HomeworkApplication.class -> 실행
> 
> OrderCommandLineRunner.class -> 콘솔 뷰 로직
> 
> jvava.resources -> application.yml , data.sql , schema.sql 등록

테스트코드 실행 및 위치
> MakeOrderServiceImplTest.class
> 
> test.resources -> application-test.yml , data.sql , schema.sql 등록
- 멀티쓰레드 환경 SoldOutException 테스트 -> **통과**
- 배송비 미포함 주문 테스트 -> **통과**
- 배송비 포함 주문 테스트 -> **통과**
---

## 과제 설계 아키텍처


헥사고날 아키텍처 사용이유 
- 의존성 역전(DIP)을 통해 적절한 의존성 규칙을 준수 할 수 있었다.
- 단일 책임의 원칙을 준수 할 수 있다.
  - 서비스 기능을 아주 작은 단위별로 유스케이스를 구현했다. 

아키텍처 구조


├── order<br>
│   ├── adapter<br>
│   │   ├── in<br>
│   │   │   └── web(controller)<br>
│   │   └── out<br>
│   │       └── persistence<br>
│   ├── usecase<br>
│   │   ├── port<br>
│   │   │   ├── in<br>
│   │   │   └── out<br>
│   │   └── service<br>
│   └── domain<br>
└── common<br>

###헥사고날 아키텍처 API의 간단한 흐름<br>
1. orer.adapter.in.web -> controller(class)<br>
2. order.port.in -> usecase(interface)<br>
3. order.usecase.service -> serviceImpl(implements usecase)<br>
4. order.usecase.port.out -> dbport(interface)<br>
5. order.adapter.out.persistence -> dbAdapter(implements dbport)<br>
---