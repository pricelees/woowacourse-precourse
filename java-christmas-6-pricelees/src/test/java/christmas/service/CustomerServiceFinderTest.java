package christmas.service;

import static christmas.TestConstants.CHRISTMAS;
import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constants.menu.Menu;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CustomerServiceFinderTest {
    @DisplayName("고객에 따라 다른 CustomerService 객체를 반환하는지 확인.")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideDifferentCustomer")
    void find(String testName, LocalDateTime dateToVisit, Map<Menu, Integer> menuToOrder,
              Class<CustomerService> expectedService) {
        CustomerService actual = CustomerServiceFinder.find(dateToVisit, menuToOrder);

        assertThat(actual.getClass()).isEqualTo(expectedService);
    }

    static Stream<Arguments> provideDifferentCustomer() {
        return Stream.of(
                arguments("주문 금액이 1만원 미만인 경우",
                        LocalDateTime.of(2023, 12, CHRISTMAS, HOUR_17, MINUTE_00),
                        Map.of(Menu.MUSHROOM_SOUP, 1, Menu.ZERO_COKE, 1),
                        CustomerServiceWithoutDiscount.class
                ),
                arguments("주문 금액이 1만원 이상인 경우",
                        LocalDateTime.of(2023, 12, CHRISTMAS, HOUR_17, MINUTE_00),
                        Map.of(Menu.TAPAS, 2),
                        CustomerServiceWithDiscount.class
                )
        );
    }
}