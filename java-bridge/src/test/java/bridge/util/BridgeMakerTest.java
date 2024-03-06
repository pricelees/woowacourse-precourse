package bridge.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BridgeMakerTest {
    @ParameterizedTest
    @MethodSource("provideArguments")
    void makeBridge_WithAllCase(Integer first, Integer second, List<String> expected) {
        BridgeNumberGenerator bridgeNumberGenerator = mock(BridgeRandomNumberGenerator.class);
        BridgeMaker bridgeMaker = new BridgeMaker(bridgeNumberGenerator);
        when(bridgeNumberGenerator.generate())
                .thenReturn(first, second);

        assertThat(bridgeMaker.makeBridge(2)).isEqualTo(expected);
    }

    static Stream<Arguments> provideArguments() {
        return Stream.of(
                arguments(0, 0, List.of("D", "D")),
                arguments(0, 1, List.of("D", "U")),
                arguments(1, 0, List.of("U", "D")),
                arguments(1, 1, List.of("U", "U"))
        );
    }
}