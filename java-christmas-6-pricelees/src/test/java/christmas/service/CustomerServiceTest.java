package christmas.service;

import static christmas.TestConstants.CHRISTMAS;
import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.constants.menu.Menu;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerServiceTest {
    /**
     * CustomerService에서 공통적으로 정의된 메서드 중, 메뉴 출력에 대해서만 테스트합니다.
     */
    @DisplayName("1만원 미만 주문금액일 때 주문 메뉴 출력 확인")
    @Test
    void showOrderedMenu_WithCannotParticipateEvent() {
        CustomerService customerService = CustomerServiceFinder.find(
                LocalDateTime.of(2023, 12, CHRISTMAS, HOUR_17, MINUTE_00),
                Map.of(Menu.MUSHROOM_SOUP, 1, Menu.ZERO_COKE, 1)
        );

        String[] expectedPrintedMenu = {"양송이수프 1개", "제로콜라 1개"};
        assertThat(customerService.showOrderedMenu()).contains(expectedPrintedMenu);
    }

    @DisplayName("1만원 이상 주문금액일 때 주문 메뉴 출력 확인")
    @Test
    void showOrderedMenu_WithCanParticipateEvent() {
        CustomerService customerService = CustomerServiceFinder.find(
                LocalDateTime.of(2023, 12, CHRISTMAS, HOUR_17, MINUTE_00),
                Map.of(Menu.CAESAR_SALAD, 1, Menu.T_BONE_STEAK, 1,
                        Menu.ICE_CREAM, 1, Menu.RED_WINE, 1)
        );

        String[] expectedPrintedMenu = {"시저샐러드 1개", "티본스테이크 1개", "아이스크림 1개", "레드와인 1개"};
        assertThat(customerService.showOrderedMenu()).contains(expectedPrintedMenu);
    }
}