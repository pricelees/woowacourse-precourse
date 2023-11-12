package christmas.discount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.constants.menu.Menu;
import christmas.constants.time.EventTime;
import christmas.domain.Customer;
import christmas.domain.SelectedMenu;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SpecialDiscountTest {
    DiscountStrategy discountStrategy = new SpecialDiscount();
    // 날짜만 테스트하므로, 메뉴는 임의로 설정
    SelectedMenu selectedMenu = new SelectedMenu(Map.of(Menu.CAESAR_SALAD, 10));

    @DisplayName("특별 할인에 해당되지 않는 모든 일에 대한 예외 발생 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideNotSpecialDay")
    void getDiscountAmount_WithNotSpecialDay_ThorwsException(int dayOfMonthToVisit) {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, dayOfMonthToVisit, 0, 0, 0),
                selectedMenu
        );
        assertThatThrownBy(() -> discountStrategy.getDiscountAmount(customer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 특별 할인이 적용되는 날이 아닙니다.");
    }

    @DisplayName("특별 할인에 해당되는 모든 날짜에 대해 정확한 할인 금액을 반환하는지 확인합니다.")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideSpecialDay")
    void getDiscountAmount_WithSpecialDay_(int dayOfMonthToVisit) {
        Customer customer = new Customer(
                LocalDateTime.of(2023, 12, dayOfMonthToVisit, 0, 0, 0),
                selectedMenu
        );

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