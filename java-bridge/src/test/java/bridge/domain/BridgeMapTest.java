package bridge.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BridgeMapTest {
    BridgeMap bridgeMap;

    @BeforeEach
    void setUp() {
        bridgeMap = new BridgeMap();
    }

    @DisplayName("입력에 따라 정확히 지도를 업데이트하는지 확인")
    @Test
    void updateMap() {
        bridgeMap.updateMap("D", " O ");
        bridgeMap.updateMap("U", " X ");
        bridgeMap.updateMap("U", " O ");

        BridgeMap expected = new BridgeMap(
                List.of("   ", " X ", " O "),
                List.of(" O ", "   ", "   ")
        );

        assertThat(bridgeMap).isEqualTo(expected);
    }

    @DisplayName("지도를 업데이트 할 때의 예외 발생 확인")
    @ParameterizedTest(name = "{2}")
    @MethodSource("provideInvalidInputForUpdateMap")
    void updateMap_WithInvalidInput(String location, String symbol, String expectedErrorMessage) {
        assertThatThrownBy(() -> bridgeMap.updateMap(location, symbol))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedErrorMessage);
    }

    @DisplayName("생성자에서의 예외 발생 확인")
    @Test
    void constructor_WithInvalidInput() {
        // UPSide는 정상 입력
        List<String> symbolsOfUPSide = List.of(" O ", " X ", "   ");
        // DOWNSide가 잘못된 기호를 가지도록 설정
        List<String> symbolsOfDOWNSide = List.of(" A ", " U ", " O ");

        assertThatThrownBy(() -> new BridgeMap(symbolsOfUPSide, symbolsOfDOWNSide))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효하지 않은 지도 기호");
    }


    static Stream<Arguments> provideInvalidInputForUpdateMap() {
        return Stream.of(
                arguments("A", " O ", "유효하지 않은 이동 위치"),
                arguments("U", " ", "유효하지 않은 지도 기호")
        );
    }
}