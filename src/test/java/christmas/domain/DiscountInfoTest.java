package christmas.domain;

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

class DiscountInfoTest {
    // 모든 메뉴를 1개씩 선택했을 때의 할인 전 금액
    private static final int AMOUNT_BEFORE_DISCOUNT = 296500;
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


    @DisplayName("정확한 '혜택' 금액을 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideDateAndExpectedAmount")
    void calculateBenefitsAmount(int dayOfMonthToVisit, List<Integer> expectedAmount) {
        LocalDateTime dateToVisit = LocalDateTime.of(2023, 12, dayOfMonthToVisit, 0, 0);
        Customer customer = new Customer(dateToVisit, selectedMenu);
        DiscountInfo discountInfo = DiscountInfo.valueOf(customer);

        int expectedBenefitsAmount = expectedAmount.get(0);
        assertThat(discountInfo.calculateBenefitsAmount()).isEqualTo(expectedBenefitsAmount);
    }

    @DisplayName("정확한 실제 할인 금액을 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideDateAndExpectedAmount")
    void calculateDiscountAmount(int dayOfMonthToVisit, List<Integer> expectedAmount) {
        LocalDateTime dateToVisit = LocalDateTime.of(2023, 12, dayOfMonthToVisit, 0, 0);
        Customer customer = new Customer(dateToVisit, selectedMenu);
        DiscountInfo discountInfo = DiscountInfo.valueOf(customer);

        int expectedActualDiscountAmount = expectedAmount.get(1);
        assertThat(discountInfo.calculateDiscountAmount()).isEqualTo(expectedActualDiscountAmount);
    }

    @DisplayName("정확한 할인 후 금액을 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideDateAndExpectedAmount")
    void calculateAmountAfterDiscount(int dayOfMonthToVisit, List<Integer> expectedAmount) {
        LocalDateTime dateToVisit = LocalDateTime.of(2023, 12, dayOfMonthToVisit, 0, 0);
        Customer customer = new Customer(dateToVisit, selectedMenu);
        DiscountInfo discountInfo = DiscountInfo.valueOf(customer);

        int expectedAmountToPay = AMOUNT_BEFORE_DISCOUNT + expectedAmount.get(1);
        assertThat(discountInfo.calculateAmountAfterDiscount()).isEqualTo(expectedAmountToPay);
    }

    static Stream<Arguments> provideDateAndExpectedAmount() {
        // 날짜는 일,월,화,수,목,금,토가 모두 포함되도록 지정
        // 메서드의 재사용을 위해, 리스트의 첫 번째 원소는 예상 혜택 금액, 두 번째 원소는 예상 할인 금액으로 지정
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
}