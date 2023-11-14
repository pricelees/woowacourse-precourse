package christmas.discount;

import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.constants.menu.Menu;
import christmas.constants.time.EventTime;
import christmas.domain.Customer;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SpecialDiscountTest {
    // 날짜만 테스트하므로, 메뉴는 임의로 설정
    Map<Menu, Integer> menuToOrder = Map.of(Menu.CAESAR_SALAD, 10);

    @DisplayName("특별 할인에 해당되지 않는 모든 일의 할인 금액이 0원인지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideNotSpecialDay")
    void getDiscountAmount_WithNotSpecialDay_ThrowsException(int dayOfMonthToVisit) {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, dayOfMonthToVisit, HOUR_17, MINUTE_00),
                menuToOrder
        );
        DiscountStrategy discountStrategy = SpecialDiscount.getInstance();

        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(0);
    }

    @DisplayName("특별 할인에 해당되는 모든 일의 할인 금액이 1000원인지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideSpecialDay")
    void getDiscountAmount_WithSpecialDay(int dayOfMonthToVisit) {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, dayOfMonthToVisit, HOUR_17, MINUTE_00),
                menuToOrder
        );
        DiscountStrategy discountStrategy = SpecialDiscount.getInstance();

        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(-1_000);
    }

    static Stream<Arguments> provideNotSpecialDay() {
        return IntStream.rangeClosed(1, 31)
                .filter(day -> !EventTime.SPECIAL_DISCOUNT_DAYS.contains(day))
                .mapToObj(Arguments::arguments);
    }

    static Stream<Arguments> provideSpecialDay() {
        return EventTime.SPECIAL_DISCOUNT_DAYS.stream()
                .map(Arguments::arguments);
    }
}