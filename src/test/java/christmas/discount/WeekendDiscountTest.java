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

class WeekendDiscountTest {
    @DisplayName("평일 방문 고객의 할인 금액이 0원인지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideWeekday")
    void getDiscountAmount_WithWeekday_ThrowsException(int dayOfMonth) {
        LocalDateTime dateToVisit = LocalDateTime.of(2023, 12, dayOfMonth, HOUR_17, MINUTE_00);
        // 메뉴는 테스트와 무관하므로 임의 설정
        Map<Menu, Integer> menuToOrder = Map.of(Menu.TAPAS, 1, Menu.CHRISTMAS_PASTA, 1);
        Customer customer = new Customer(dateToVisit, menuToOrder);
        DiscountStrategy discountStrategy = WeekendDiscount.getInstance();

        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(0);
    }

    @DisplayName("정상적인 입력에 대한 정확한 할인 금액 반환 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideValidArgument")
    void getDiscountAmount_WithWeekend(
            String testName,
            LocalDateTime dateToVisit,
            SelectedMenu selectedMenu
    ) {
        DateToVisit date = new DateToVisit(dateToVisit);
        Customer customer = new Customer(date, selectedMenu);
        DiscountStrategy discountStrategy = WeekendDiscount.getInstance();

        int expectedDiscountAmount = -(2023 * selectedMenu.getMainMenuCounts());
        assertThat(discountStrategy.getDiscountAmount(customer))
                .isEqualTo(expectedDiscountAmount);
    }

    static Stream<Arguments> provideWeekday() {
        return IntStream.rangeClosed(1, 31)
                .mapToObj(day -> LocalDateTime.of(2023, 12, day, HOUR_17, MINUTE_00))
                .filter(day -> EventTime.WEEKDAYS.contains(day.getDayOfWeek()))
                .map(day -> Arguments.arguments(day.getDayOfMonth()));
    }

    // 매 주마다 금,토 중 하나를 선정
    static Stream<Arguments> provideValidArgument() {
        return Stream.of(
                arguments(
                        "12월 1일 금요일, 메인 메뉴 1개",
                        LocalDateTime.of(2023, 12, 1, HOUR_17, MINUTE_00),
                        new SelectedMenu(Map.of(Menu.T_BONE_STEAK, 1))
                ),
                arguments(
                        "12월 9일 토요일, 메인 메뉴 2개",
                        LocalDateTime.of(2023, 12, 9, HOUR_17, MINUTE_00),
                        new SelectedMenu(Map.of(Menu.T_BONE_STEAK, 1, Menu.BBQ_RIBS, 1))
                ),
                arguments(
                        "12월 15일 금요일, 메인 메뉴 3개",
                        LocalDateTime.of(2023, 12, 15, HOUR_17, MINUTE_00),
                        new SelectedMenu(Map.of(Menu.T_BONE_STEAK, 1, Menu.BBQ_RIBS, 1,
                                Menu.CHRISTMAS_PASTA, 1))
                ),
                arguments(
                        "12월 23일 토요일, 메인 메뉴 4개",
                        LocalDateTime.of(2023, 12, 23, HOUR_17, MINUTE_00),
                        new SelectedMenu(Map.of(Menu.T_BONE_STEAK, 1, Menu.BBQ_RIBS, 1,
                                Menu.SEAFOOD_PASTA, 1, Menu.CHRISTMAS_PASTA, 1))
                ),
                arguments(
                        "12월 29일 금요일, 메인 메뉴 5개",
                        LocalDateTime.of(2023, 12, 29, HOUR_17, MINUTE_00),
                        new SelectedMenu(Map.of(Menu.T_BONE_STEAK, 2, Menu.BBQ_RIBS, 1,
                                Menu.SEAFOOD_PASTA, 1, Menu.CHRISTMAS_PASTA, 1))
                )
        );
    }
}