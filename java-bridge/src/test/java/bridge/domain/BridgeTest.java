package bridge.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import bridge.util.BridgeMaker;
import bridge.util.BridgeNumberGenerator;
import bridge.util.BridgeRandomNumberGenerator;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class BridgeTest {
    @DisplayName("생성자에서의 잘못된 위치 입력에 대한 예외 발생 확인")
    @ParameterizedTest(name = "{1}")
    @MethodSource("provideInvalidInputForConstructor")
    void constructor_WithInvalidLocation_ThrowsException(List<String> locationsCanMove, String expectedErrorMessage) {
        assertThatThrownBy(() -> new Bridge(locationsCanMove))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedErrorMessage);
    }

    @DisplayName("팩토리 메서드에서의 잘못된 다리 길이에 대한 예외 발생 확인")
    @Test
    void from_WithInvalidBridgeSize_ThrowsException() {
        BridgeNumberGenerator bridgeNumberGenerator = new BridgeRandomNumberGenerator();
        BridgeMaker bridgeMaker = new BridgeMaker(bridgeNumberGenerator);

        assertThatThrownBy(() -> Bridge.from(bridgeMaker, 2)) // 최소 다리 길이인 3보다 작은 값 입력
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효하지 않은 다리 길이");
    }


    @DisplayName("다리를 건널 수 있는지에 대한 여부를 정확히 반환하는지 확인")
    @ParameterizedTest
    @CsvSource(value = {"U / 0 / true", "D / 0 / false"}, delimiter = '/')
    void canMove(String location, int step, boolean expected) {
        Bridge bridge = new Bridge(List.of("U", "U", "U"));
        assertThat(bridge.canMove(location, step)).isEqualTo(expected);
    }

    @DisplayName("다리를 건널 수 있는지 여부를 확인할 때의 잘못된 입력값에 대한 예외 발생 확인")
    @ParameterizedTest(name = "{2}")
    @MethodSource("provideInvalidInputForCanMove")
    void canMove_WithInvalidInput(String location, int step, String expectedErrorMessage) {
        Bridge bridge = new Bridge(List.of("U", "U", "U"));
        assertThatThrownBy(() -> bridge.canMove(location, step))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedErrorMessage);

    }

    static Stream<Arguments> provideInvalidInputForCanMove() {
        return Stream.of(
                arguments("U", 4, "유효하지 않은 이동 단계"),
                arguments("A", 3, "유효하지 않은 이동 위치")
        );
    }

    static Stream<Arguments> provideInvalidInputForConstructor() {
        return Stream.of(
                arguments(List.of("U", "A", "D"), "유효하지 않은 이동 위치"),
                arguments(List.of("U", "U"), "유효하지 않은 다리 길이")
        );
    }
}