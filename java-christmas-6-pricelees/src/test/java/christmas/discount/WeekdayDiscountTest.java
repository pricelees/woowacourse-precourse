package christmas.discount;

import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constants.menu.Menu;
import christmas.constants.time.EventTime;
import christmas.domain.Customer;
import christmas.domain.DateToVisit;
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
    @DisplayName("주말 방문 고객의 할인 금액이 0원인지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideWeekend")
    void getDiscountAmount_WithWeekend_ThrowsException(int dayOfMonth) {
        LocalDateTime dateToVisit = LocalDateTime.of(2023, 12, dayOfMonth, HOUR_17, MINUTE_00);
        // 메뉴는 테스트와 무관하므로 임의 설정
        Map<Menu, Integer> menuToOrder = Map.of(Menu.TAPAS, 1, Menu.CHRISTMAS_PASTA, 1);
        Customer customer = new Customer(dateToVisit, menuToOrder);

        DiscountStrategy discountStrategy = WeekdayDiscount.getInstance();
        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(0);
    }

    @DisplayName("평일 방문 고객의 할인 금액을 정확히 계산하는지 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideValidArgument")
    void getDiscountAmount_WithWeekday(
            String testName,
            LocalDateTime dateToVisit,
            Map<Menu, Integer> menuToOrder
    ) {
        DateToVisit date = new DateToVisit(dateToVisit);
        SelectedMenu selectedMenu = new SelectedMenu(menuToOrder);
        Customer customer = new Customer(date, selectedMenu);
        DiscountStrategy discountStrategy = WeekdayDiscount.getInstance();

        int expectedDiscountAmount = -(2023 * selectedMenu.getDessertCounts());
        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(expectedDiscountAmount);
    }

    static Stream<Arguments> provideWeekend() {
        return IntStream.rangeClosed(1, 31)
                .mapToObj(day -> LocalDateTime.of(2023, 12, day, HOUR_17, MINUTE_00))
                .filter(day -> EventTime.WEEKENDS.contains(day.getDayOfWeek()))
                .map(day -> Arguments.arguments(day.getDayOfMonth()));
    }

    // 모든 평일에 해당하는 요일(일 ~ 목)이 포함되도록 설정.
    static Stream<Arguments> provideValidArgument() {
        return Stream.of(
                arguments(
                        "12월 3일 일요일, 디저트 개수 1개",
                        LocalDateTime.of(2023, 12, 3, HOUR_17, MINUTE_00),
                        Map.of(Menu.CHOCOLATE_CAKE, 1)
                ),
                arguments(
                        "12월 11일 월요일, 디저트 개수 2개",
                        LocalDateTime.of(2023, 12, 11, HOUR_17, MINUTE_00),
                        Map.of(Menu.CHOCOLATE_CAKE, 1, Menu.ICE_CREAM, 1)
                ),
                arguments(
                        "12월 19일 화요일, 디저트 개수 3개",
                        LocalDateTime.of(2023, 12, 19, HOUR_17, MINUTE_00),
                        Map.of(Menu.CHOCOLATE_CAKE, 2, Menu.ICE_CREAM, 1)
                ),
                arguments(
                        "12월 27일 수요일, 디저트 개수 4개",
                        LocalDateTime.of(2023, 12, 27, HOUR_17, MINUTE_00),
                        Map.of(Menu.CHOCOLATE_CAKE, 2, Menu.ICE_CREAM, 2)
                ),
                arguments(
                        "12월 28일 목요일, 디저트 개수 5개",
                        LocalDateTime.of(2023, 12, 28, HOUR_17, MINUTE_00),
                        Map.of(Menu.CHOCOLATE_CAKE, 3, Menu.ICE_CREAM, 2)
                ),
                arguments(
                        "12월 31일 일요일, 디저트 개수 6개",
                        LocalDateTime.of(2023, 12, 31, HOUR_17, MINUTE_00),
                        Map.of(Menu.CHOCOLATE_CAKE, 3, Menu.ICE_CREAM, 3)
                )
        );
    }
}