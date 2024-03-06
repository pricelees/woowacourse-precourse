package christmas.service;

import static christmas.TestConstants.CHRISTMAS;
import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import camp.nextstep.edu.missionutils.Randoms;
import christmas.constants.menu.Menu;
import christmas.domain.Customer;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CustomerServiceWithDiscountTest {
    // 모든 메뉴를 1개씩 선택하고 테스트 진행
    Map<Menu, Integer> menuToOrder;

    @BeforeEach
    void setUp() {
        Map<Menu, Integer> allMenu = new EnumMap<>(Menu.class);
        for (Menu menu : Menu.values()) {
            allMenu.put(menu, 1);
        }
        menuToOrder = allMenu;
    }

    @DisplayName("모든 메뉴를 1개씩 주문했을 때, 증정 메뉴 내역을 정확히 반환하는지 확인")
    @Test
    void showFreeMenu_WithAllMenu() {
        // 증정 메뉴는 금액으로 결정되기에, 1일부터 31일까지 무작위 선택
        int randomDayOfMonth = Randoms.pickNumberInRange(1, 31);
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, randomDayOfMonth, HOUR_17, MINUTE_00),
                menuToOrder
        );
        CustomerService customerService = new CustomerServiceWithDiscount(customer);
        String expectedMessage = "샴페인 1개";

        assertThat(customerService.showFreeMenu()).isEqualTo(expectedMessage);
    }

    @DisplayName("모든 메뉴를 1개씩 주문했을 때, 할인 내역을 정확히 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideDiscountDescription")
    void showDiscountDescription_WithAllMenu(int dayOfMonthToVisit, String[] expectedDiscountDescription) {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, dayOfMonthToVisit, HOUR_17, MINUTE_00),
                menuToOrder
        );
        CustomerService customerService = new CustomerServiceWithDiscount(customer);

        assertThat(customerService.showDiscountDescription()).contains(expectedDiscountDescription);
    }

    @DisplayName("모든 메뉴를 1개씩 주문했을 때, 총 혜택 금액을 정확히 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideExpectedAmount")
    void showTotalBenefitsAmount_WithAllMenu(int dayOfMonthToVisit, List<String> expectedAmounts) {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, dayOfMonthToVisit, HOUR_17, MINUTE_00),
                menuToOrder
        );
        CustomerService customerService = new CustomerServiceWithDiscount(customer);
        String expectedMessage = expectedAmounts.get(0);

        assertThat(customerService.showTotalBenefitsAmount()).isEqualTo(expectedMessage);
    }

    @DisplayName("모든 메뉴를 1개씩 주문했을 때, 예상 결제 금액을 정확히 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideExpectedAmount")
    void showExpectedAmountToPay_WithAllMenu(int dayOfMonthToVisit, List<String> expectedAmounts) {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, dayOfMonthToVisit, HOUR_17, MINUTE_00),
                menuToOrder
        );
        CustomerService customerService = new CustomerServiceWithDiscount(customer);
        String expectedMessage = expectedAmounts.get(1);

        assertThat(customerService.showExpectedAmountToPay()).isEqualTo(expectedMessage);
    }

    @DisplayName("모든 메뉴를 1개씩 주문 했을 때, 증정될 배지를 정확히 반환하는지 확인")
    @Test
    void showEventBadge_WithAllMenu() {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, CHRISTMAS, HOUR_17, MINUTE_00),
                menuToOrder
        );
        CustomerService customerService = new CustomerServiceWithDiscount(customer);
        String expectedBadgeName = "산타";

        assertThat(customerService.showEventBadge()).isEqualTo(expectedBadgeName);
    }

    static Stream<Arguments> provideDiscountDescription() {
        return Stream.of(
                arguments(1, new String[]{"크리스마스 디데이 할인: -1,000원", "주말 할인: -8,092원", "증정 이벤트: -25,000원"}),
                arguments(3, new String[]{"크리스마스 디데이 할인: -1,200원", "평일 할인: -4,046원",
                        "특별 할인: -1,000원", "증정 이벤트: -25,000원"}),
                arguments(5, new String[]{"크리스마스 디데이 할인: -1,400원", "평일 할인: -4,046원", "증정 이벤트: -25,000원"}),
                arguments(9, new String[]{"크리스마스 디데이 할인: -1,800원", "주말 할인: -8,092원", "증정 이벤트: -25,000원"}),
                arguments(25, new String[]{"크리스마스 디데이 할인: -3,400원", "평일 할인: -4,046원"
                        , "특별 할인: -1,000원", "증정 이벤트: -25,000원"}),
                arguments(30, new String[]{"주말 할인: -8,092원", "증정 이벤트: -25,000원"}),
                arguments(31, new String[]{"평일 할인: -4,046원", "특별 할인: -1,000원", "증정 이벤트: -25,000원"})
        );
    }

    static Stream<Arguments> provideExpectedAmount() {
        // 리스트의 원소는 순서대로 총 혜택 금액, 실제 할인 금액, 할인 후 예상 결제 금액
        return Stream.of(
                arguments(1, List.of("-34,092원", "287,408원")),
                arguments(3, List.of("-31,246원", "290,254원")),
                arguments(5, List.of("-30,446원", "291,054원")),
                arguments(9, List.of("-34,892원", "286,608원")),
                arguments(25, List.of("-33,446원", "288,054원")),
                arguments(30, List.of("-33,092원", "288,408원")),
                arguments(31, List.of("-30,046원", "291,454원"))
        );
    }
}