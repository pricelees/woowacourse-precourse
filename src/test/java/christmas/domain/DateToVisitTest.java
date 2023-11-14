package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import camp.nextstep.edu.missionutils.Randoms;
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
        // 일,시,분은 테스트와 무관하므로 랜덤값으로 지정
        int randomDayOfMonth = Randoms.pickNumberInRange(1, 31);
        int randomHour = Randoms.pickNumberInRange(0, 24);
        int randomMinute = Randoms.pickNumberInRange(0, 60);
        return Stream.of(
                arguments(
                        "2024년 12월인 경우",
                        LocalDateTime.of(2024, 12, randomDayOfMonth, randomHour, randomMinute),
                        "[ERROR] 이벤트는 2023년에 진행됩니다."
                ),
                arguments(
                        "2023년 11월인 경우",
                        LocalDateTime.of(2023, 11, randomDayOfMonth, randomHour, randomMinute),
                        "[ERROR] 이벤트는 12월에만 진행됩니다."
                )
        );
    }
}