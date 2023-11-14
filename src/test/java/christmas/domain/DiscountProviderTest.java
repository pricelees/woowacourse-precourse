package christmas.domain;

import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constants.menu.Menu;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountProviderTest {
    SelectedMenu selectedMenu;

    @BeforeEach
    void setUp() {
        // 모든 메뉴를 모두 1개씩 선택하고 테스트 진행
        Map<Menu, Integer> allMenu = new EnumMap<>(Menu.class);
        for (Menu menu : Menu.values()) {
            allMenu.put(menu, 1);
        }
        selectedMenu = new SelectedMenu(allMenu);
    }


    @DisplayName("모든 메뉴를 1개씩 선택했을 때, 정확한 '혜택' 금액을 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideExpectedAmount")
    void calculateBenefitsAmount_WithAllMenu(int dayOfMonthToVisit, List<Integer> expectedAmount) {
        DateToVisit dateToVisit = new DateToVisit(LocalDateTime.of(
                2023, 12, dayOfMonthToVisit, HOUR_17, MINUTE_00
        ));
        Customer customer = new Customer(dateToVisit, selectedMenu);

        int expectedBenefitsAmount = expectedAmount.get(0);
        assertThat(DiscountProvider.totalBenefitsAmount(customer)).isEqualTo(expectedBenefitsAmount);
    }

    @DisplayName("모든 메뉴를 1개씩 선택했을 때, 정확한 실제 할인 금액을 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideExpectedAmount")
    void calculateDiscountAmount_WithAllMenu(int dayOfMonthToVisit, List<Integer> expectedAmount) {
        DateToVisit dateToVisit = new DateToVisit(LocalDateTime.of(
                2023, 12, dayOfMonthToVisit, HOUR_17, MINUTE_00
        ));
        Customer customer = new Customer(dateToVisit, selectedMenu);

        int expectedActualDiscountAmount = expectedAmount.get(1);
        assertThat(DiscountProvider.actualDiscountAmount(customer)).isEqualTo(expectedActualDiscountAmount);
    }

    @DisplayName("모든 메뉴를 1개씩 선택했을 때, 정확한 할인 내역을 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideExpectedDescription")
    void getDiscountDescription_WithAllMenu(int dayOfMonthToVisit, String[] expectedDiscounts) {
        DateToVisit dateToVisit = new DateToVisit(LocalDateTime.of(
                2023, 12, dayOfMonthToVisit, HOUR_17, MINUTE_00
        ));
        Customer customer = new Customer(dateToVisit, selectedMenu);

        assertThat(DiscountProvider.allDiscountsDescription(customer)).contains(expectedDiscounts);
    }

    static Stream<Arguments> provideExpectedAmount() {
        // 날짜는 일,월,화,수,목,금,토가 모두 포함되도록 지정
        // 메서드의 재사용을 위해, 리스트의 첫 번째 원소는 예상 혜택 금액, 두 번째 원소는 예상 할인 금액으로 지정
        // 세 번째 파라미터는 예상되는 할인 내역
        return Stream.of(
                arguments(1, List.of(-(2023 * 4 + 1000 + 25000), -(2023 * 4 + 1000))),
                arguments(2, List.of(-(2023 * 4 + 1100 + 25000), -(2023 * 4 + 1100))),
                arguments(7, List.of(-(2023 * 2 + 1600 + 25000), -(2023 * 2 + 1600))),
                arguments(13, List.of(-(2023 * 2 + 2200 + 25000), -(2023 * 2 + 2200))),
                arguments(19, List.of(-(2023 * 2 + 2800 + 25000), -(2023 * 2 + 2800))),
                arguments(25, List.of(-(2023 * 2 + 3400 + 1000 + 25000), -(2023 * 2 + 3400 + 1000))),
                arguments(31, List.of(-(2023 * 2 + 1000 + 25000), -(2023 * 2 + 1000)))
        );
    }

    static Stream<Arguments> provideExpectedDescription() {
        // 날짜는 일,월,화,수,목,금,토가 모두 포함되도록 지정
        // 메서드의 재사용을 위해, 리스트의 첫 번째 원소는 예상 혜택 금액, 두 번째 원소는 예상 할인 금액으로 지정
        // 세 번째 파라미터는 예상되는 할인 내역
        return Stream.of(
                arguments(1, new String[]{"크리스마스 디데이 할인: -1,000원", "주말 할인: -8,092원", "증정 이벤트: -25,000원"}),
                arguments(2, new String[]{"크리스마스 디데이 할인: -1,100원", "주말 할인: -8,092원", "증정 이벤트: -25,000원"}),
                arguments(7, new String[]{"크리스마스 디데이 할인: -1,600원", "평일 할인: -4,046원", "증정 이벤트: -25,000원"}),
                arguments(13, new String[]{"크리스마스 디데이 할인: -2,200원", "평일 할인: -4,046원", "증정 이벤트: -25,000원"}),
                arguments(19, new String[]{"크리스마스 디데이 할인: -2,800원", "평일 할인: -4,046원", "증정 이벤트: -25,000원"}),
                arguments(25, new String[]{"크리스마스 디데이 할인: -3,400원", "평일 할인: -4,046원", "증정 이벤트: -25,000원"}),
                arguments(31, new String[]{"평일 할인: -4,046원", "특별 할인: -1,000원", "증정 이벤트: -25,000원"})
        );
    }
}