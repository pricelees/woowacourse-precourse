package christmas.discount;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountStrategyTest {
    @DisplayName("모든 구현 클래스에서의 연,월에 대한 예외 발생 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidYearAndMonthProvider")
    void validateYearAndMonth(
            String testName,
            DiscountStrategy discountStrategy,
            LocalDateTime dateToVisit
    ) {
        assertThatThrownBy(() -> discountStrategy.validateYearAndMonth(dateToVisit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이벤트는 2023년 12월에만 진행됩니다.");
    }

    static Stream<Arguments> invalidYearAndMonthProvider() {
        return Stream.of(
                arguments("2024년 12월 1일인 경우 - 크리스마스 디데이 할인",
                        new ChristmasDiscount(),
                        LocalDateTime.of(2024, 12, 1, 0, 0, 0)
                ),
                arguments("2023년 11월 31인 경우 - 크리스마스 디데이 할인",
                        new ChristmasDiscount(),
                        LocalDateTime.of(2023, 11, 30, 0, 0, 0)
                ),
                arguments("2024년 12월 1일인 경우 - 샴페인 증정",
                        new ChampagneDiscount(),
                        LocalDateTime.of(2024, 12, 1, 0, 0, 0)
                ),
                arguments("2023년 11월 31인 경우 - 샴페인 증정",
                        new ChampagneDiscount(),
                        LocalDateTime.of(2023, 11, 30, 0, 0, 0)
                ),
                arguments("2024년 12월 1일인 경우 - 특별 할인",
                        new SpecialDiscount(),
                        LocalDateTime.of(2024, 12, 1, 0, 0, 0)
                ),
                arguments("2023년 11월 31인 경우 - 특별 할인",
                        new SpecialDiscount(),
                        LocalDateTime.of(2023, 11, 30, 0, 0, 0)
                ),
                arguments("2024년 12월 1일인 경우 - 주말 할인",
                        new WeekendDiscount(),
                        LocalDateTime.of(2024, 12, 1, 0, 0, 0)
                ),
                arguments("2023년 11월 31인 경우 - 주말 할인",
                        new WeekendDiscount(),
                        LocalDateTime.of(2023, 11, 30, 0, 0, 0)
                ),
                arguments("2024년 12월 1일인 경우 - 평일 할인",
                        new WeekdayDiscount(),
                        LocalDateTime.of(2024, 12, 1, 0, 0, 0)
                ),
                arguments("2023년 11월 31인 경우 - 평일 할인",
                        new WeekdayDiscount(),
                        LocalDateTime.of(2023, 11, 30, 0, 0, 0)
                )
        );
    }
}