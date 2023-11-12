package christmas.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.discount.ChristmasDiscount;
import christmas.discount.DiscountStrategy;
import christmas.discount.SpecialDiscount;
import christmas.discount.WeekdayDiscount;
import christmas.discount.WeekendDiscount;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountProviderByDateTest {
    static Stream<Arguments> provideInvalidDate() {
        return Stream.of(
                arguments("2023년이 아닌 경우",
                        LocalDateTime.of(2022, 12, 1, 0, 0)),
                arguments("12월이 아닌 경우",
                        LocalDateTime.of(2023, 11, 30, 0, 0))
        );
    }

    static Stream<Arguments> provideExpectedDiscountList() {
        // 가능한 모든 할인 조합을 반환
        return Stream.of(
                arguments("12월 1일 : 크리스마스 디데이 할인, 주말 할인",
                        1, List.of(ChristmasDiscount.class, WeekendDiscount.class)),
                arguments("12월 3일 : 크리스마스 디데이 할인, 평일 할인, 특별 할인",
                        3, List.of(ChristmasDiscount.class, WeekdayDiscount.class, SpecialDiscount.class)),
                arguments("12월 7일 : 크리스마스 디데이 할인, 평일 할인",
                        7, List.of(ChristmasDiscount.class, WeekdayDiscount.class)),
                arguments("12월 25일 : 크리스마스 디데이 할인, 평일 할인, 특별 할인",
                        25, List.of(ChristmasDiscount.class, WeekdayDiscount.class, SpecialDiscount.class)),
                arguments("12월 26일 : 평일 할인",
                        26, List.of(WeekdayDiscount.class)),
                arguments("12월 30일 : 주말 할인",
                        30, List.of(WeekendDiscount.class)),
                arguments("12월 31일 : 평일 할인, 특별 할인",
                        31, List.of(WeekdayDiscount.class, SpecialDiscount.class))
        );
    }

    @DisplayName("입력된 날짜에 대한 예외 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideInvalidDate")
    void provide_WithInvalidInput_ThrowsException(String testName, LocalDateTime dateToVisit) {
        assertThatThrownBy(() -> new DiscountProviderByDate(dateToVisit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이벤트는 2023년 12월에만 진행됩니다.");
    }

    @DisplayName("입력된 일자에 대한 정확한 할인 리스트 반환 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideExpectedDiscountList")
    void provide(String testName, int dayOfMonthToVisit, List<DiscountStrategy> expectedDiscounts) {
        LocalDateTime dateToVisit = LocalDateTime.of(2023, 12, dayOfMonthToVisit, 0, 0);
        DiscountProviderByDate provider = new DiscountProviderByDate(dateToVisit);

        assertThat(provider.provide()).extracting(DiscountStrategy::getClass)
                .isEqualTo(expectedDiscounts);
    }
}