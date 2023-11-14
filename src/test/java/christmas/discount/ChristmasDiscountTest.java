package christmas.discount;

import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constants.menu.Menu;
import christmas.domain.Customer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ChristmasDiscountTest {

    // 날짜만 테스트하므로, 메뉴는 임의로 설정
    Map<Menu, Integer> menuToOrder = Map.of(Menu.CAESAR_SALAD, 10);

    @DisplayName("방문 일자가 25일 이후일 때의 할인 금액을 0원으로 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일 : 0원")
    @ValueSource(ints = {26, 27, 28, 29, 30, 31})
    void getDiscountAmount_AfterChristmas_ThrowsException(int dayOfMonthToVisit) {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, dayOfMonthToVisit, HOUR_17, MINUTE_00),
                menuToOrder
        );
        DiscountStrategy discountStrategy = ChristmasDiscount.getInstance();

        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(0);
    }

    @DisplayName("1일부터 25일까지 모든 날짜에 대해 정확한 할인 금액을 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일 : {1}원")
    @MethodSource("provideExpectedDiscountAmountByDay")
    void getDiscountAmount_WithAllDaysBeforeChristmas(int dayOfMonthToVisit, int expectedDiscountAmount) {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, dayOfMonthToVisit, HOUR_17, MINUTE_00),
                menuToOrder
        );
        DiscountStrategy discountStrategy = ChristmasDiscount.getInstance();

        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(expectedDiscountAmount);
    }

    static Stream<Arguments> provideExpectedDiscountAmountByDay() {
        List<Arguments> argumentsList = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            argumentsList.add(arguments(i, -(1000 + 100 * (i - 1))));
        }
        return argumentsList.stream();
    }
}