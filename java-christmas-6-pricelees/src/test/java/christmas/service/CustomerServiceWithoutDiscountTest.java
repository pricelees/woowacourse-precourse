package christmas.service;

import static christmas.TestConstants.CHRISTMAS;
import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.constants.menu.Menu;
import christmas.domain.Customer;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerServiceWithoutDiscountTest {
    CustomerService customerService;

    // 주문 금액이 10000원 미만이면, 날짜와 상관없이 할인이 적용되지 않기에, 날짜는 무작위 지정
    @BeforeEach
    void setUp() {
        LocalDateTime dateToVisit = LocalDateTime.of(
                2023, 12, CHRISTMAS, HOUR_17, MINUTE_00
        );
        Map<Menu, Integer> menuToOrder = Map.of(Menu.TAPAS, 1, Menu.ZERO_COKE, 1);
        Customer customer = new Customer(dateToVisit, menuToOrder);

        customerService = new CustomerServiceWithoutDiscount(customer);
    }

    @DisplayName("이벤트 참여가 불가능할 때, 증정 메뉴 내역 출력 확인")
    @Test
    void showFreeMenu_CantParticipateEvent() {
        assertThat(customerService.showFreeMenu()).isEqualTo("없음");
    }

    @DisplayName("이벤트 참여가 불가능할 때, 할인 내역 출력 확인")
    @Test
    void showDiscountDescription_CantParticipateEven() {
        assertThat(customerService.showDiscountDescription()).isEqualTo("없음");
    }

    @DisplayName("이벤트 참여가 불가능할 때, 할인 내역 출력 확인")
    @Test
    void showEventBadge_CantParticipateEven() {
        assertThat(customerService.showEventBadge()).isEqualTo("없음");
    }

    @DisplayName("이벤트 참여가 불가능할 때, 총 혜택 금액 출력 확인")
    @Test
    void showTotalBenefitsAmount_CantParticipateEven() {
        assertThat(customerService.showTotalBenefitsAmount()).isEqualTo("0원");
    }

    @DisplayName("이벤트 참여가 불가능할 때, 예상 금액 출력 확인")
    @Test
    void showExpectedAmountToPay_CantParticipateEven() {
        assertThat(customerService.showExpectedAmountToPay()).isEqualTo("8,500원");
    }
}