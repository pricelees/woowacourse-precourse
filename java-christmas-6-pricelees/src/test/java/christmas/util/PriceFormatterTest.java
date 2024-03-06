package christmas.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PriceFormatterTest {
    @DisplayName("1원부터 10억까지 모든 단위의 숫자를 정확히 파싱하는지 확인")
    @ParameterizedTest
    @MethodSource("provideArguments")
    void format_WithVariousUnit(int amount, String expectedResult) {
        assertThat(PriceFormatter.format(amount))
                .isEqualTo(expectedResult);
    }

    static Stream<Arguments> provideArguments() {
        return Stream.of(
                arguments(1, "1원"),
                arguments(10, "10원"),
                arguments(100, "100원"),
                arguments(1_000, "1,000원"),
                arguments(10_000, "10,000원"),
                arguments(100_000, "100,000원"),
                arguments(1_000_000, "1,000,000원"),
                arguments(10_000_000, "10,000,000원"),
                arguments(100_000_000, "100,000,000원"),
                arguments(1_000_000_000, "1,000,000,000원")
        );
    }
}