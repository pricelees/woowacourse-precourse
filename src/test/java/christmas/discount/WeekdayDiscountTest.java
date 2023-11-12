package christmas.discount;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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

class WeekdayDiscountTest {
    DiscountStrategy discountStrategy = new WeekdayDiscount();

    @DisplayName("방문 일자가 주말인 경우 예외 발생 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideWeekend")
    void getDiscountAmount_WithWeekend_ThrowsException(int dayOfMonth) {
        LocalDateTime dateToVisit = LocalDateTime.of(2023, 12, dayOfMonth, 0, 0);
        // 메뉴는 테스트와 무관하므로 임의 설정
        SelectedMenu selectedMenu = new SelectedMenu(Map.of(Menu.TAPAS, 1, Menu.CHRISTMAS_PASTA, 1));
        Customer customer = new Customer(dateToVisit, selectedMenu);

        assertThatThrownBy(() -> discountStrategy.getDiscountAmount(customer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 이벤트는 평일에만 진행됩니다.");
    }

    @DisplayName("정상적인 입력에 대한 정확한 할인 금액 반환 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideValidArgument")
    void getDiscountAmount_WithValidInput(
            String testName,
            LocalDateTime dateToVisit,
            SelectedMenu selectedMenu
    ) {
        Customer customer = new Customer(dateToVisit, selectedMenu);
        int expectedDiscountAmount = -(2023 * selectedMenu.getDessertCounts());

        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(expectedDiscountAmount);
    }

    static Stream<Arguments> provideWeekend() {
        return IntStream.rangeClosed(1, 31)
                .mapToObj(day -> LocalDateTime.of(2023, 12, day, 0, 0, 0))
                .filter(day -> EventTime.WEEKENDS.contains(day.getDayOfWeek()))
                .map(day -> Arguments.arguments(day.getDayOfMonth()));
    }

    // 모든 평일에 해당하는 요일(일 ~ 목)이 포함되도록 설정.
    static Stream<Arguments> provideValidArgument() {
        return Stream.of(
                arguments(
                        "12월 3일 일요일, 디저트 개수 1개",
                        LocalDateTime.of(2023, 12, 3, 0, 0),
                        new SelectedMenu(Map.of(Menu.CHOCOLATE_CAKE, 1))
                ),
                arguments(
                        "12월 11일 월요일, 디저트 개수 2개",
                        LocalDateTime.of(2023, 12, 11, 0, 0),
                        new SelectedMenu(Map.of(Menu.CHOCOLATE_CAKE, 1, Menu.ICE_CREAM, 1))
                ),
                arguments(
                        "12월 19일 화요일, 디저트 개수 3개",
                        LocalDateTime.of(2023, 12, 19, 0, 0),
                        new SelectedMenu(Map.of(Menu.CHOCOLATE_CAKE, 2, Menu.ICE_CREAM, 1))
                ),
                arguments(
                        "12월 27일 수요일, 디저트 개수 4개",
                        LocalDateTime.of(2023, 12, 27, 0, 0),
                        new SelectedMenu(Map.of(Menu.CHOCOLATE_CAKE, 2, Menu.ICE_CREAM, 2))
                ),
                arguments(
                        "12월 28일 목요일, 디저트 개수 5개",
                        LocalDateTime.of(2023, 12, 28, 0, 0),
                        new SelectedMenu(Map.of(Menu.CHOCOLATE_CAKE, 3, Menu.ICE_CREAM, 2))
                ),
                arguments(
                        "12월 31일 일요일, 디저트 개수 6개",
                        LocalDateTime.of(2023, 12, 31, 0, 0),
                        new SelectedMenu(Map.of(Menu.CHOCOLATE_CAKE, 3, Menu.ICE_CREAM, 3))
                )
        );
    }
}