package christmas.domain;

import static christmas.TestConstants.CHRISTMAS;
import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DateToVisitTest {
    @DisplayName("2023년 12월이 아닌 경우 예외를 발생시키는지 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideInvalidYearAndMonth")
    void constructor_WithInvalidInput_ThrowsException(
            String testName,
            LocalDateTime date,
            String expectedErrorMessage
    ) {
        assertThatThrownBy(() -> new DateToVisit(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedErrorMessage);
    }

    static Stream<Arguments> provideInvalidYearAndMonth() {
        return Stream.of(
                arguments(
                        "2024년 12월인 경우",
                        LocalDateTime.of(2024, 12, CHRISTMAS, HOUR_17, MINUTE_00),
                        "[ERROR] 이벤트는 2023년에 진행됩니다."
                ),
                arguments(
                        "2023년 11월인 경우",
                        LocalDateTime.of(2023, 11, CHRISTMAS, HOUR_17, MINUTE_00),
                        "[ERROR] 이벤트는 12월에만 진행됩니다."
                )
        );
    }
}