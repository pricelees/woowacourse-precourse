package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constants.menu.Menu;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CustomerTest {
    @DisplayName("유효하지 않은 날짜 정보를 입력했을 때 예외 발생 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideInvalidDate")
    void constructor_WithInvalidDate_ThrowsException(String testName, LocalDateTime date, String expectedErrorMessage) {
        // SelectedMenu는 예외를 발생시키지 않도록 임의 지정
        SelectedMenu selectedMenu = new SelectedMenu(Map.of(Menu.TAPAS, 10));
        assertThatThrownBy(() -> new Customer(date, selectedMenu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedErrorMessage);
    }

    @DisplayName("구매 금액에 따른 샴페인 증정 여부 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideArgumentsForChampagneTest")
    void canReceiveFreeChampagne(String testName, SelectedMenu selectedMenu, boolean expectedResult) {
        // 날짜는 임의 지정
        LocalDateTime date = LocalDateTime.of(2023, 12, 3, 18, 0);
        Customer customer = new Customer(date, selectedMenu);

        assertThat(customer.canReceiveFreeChampagne()).isEqualTo(expectedResult);
    }

    @DisplayName("구매 금액에 따른 이벤트 참여 불가 여부 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideArgumentsForEventTest")
    void cannotParticipateEvent(String testName, SelectedMenu selectedMenu, boolean expectedResult) {
        // 날짜는 임의 지정
        LocalDateTime date = LocalDateTime.of(2023, 12, 25, 3, 35);
        Customer customer = new Customer(date, selectedMenu);

        assertThat(customer.cannotParticipateEvent()).isEqualTo(expectedResult);
    }

    static Stream<Arguments> provideInvalidDate() {
        return Stream.of(
                arguments("2024년을 입력한 경우",
                        LocalDateTime.of(2024, 12, 1, 0, 0),
                        "[ERROR] 이벤트는 2023년에 진행됩니다."),
                arguments("11월을 입력한 경우",
                        LocalDateTime.of(2023, 11, 1, 0, 0),
                        "[ERROR] 이벤트는 12월에만 진행됩니다.")
        );
    }

    static Stream<Arguments> provideArgumentsForChampagneTest() {
        return Stream.of(
                arguments("12만원 이상인 경우",
                        new SelectedMenu(Map.of(Menu.T_BONE_STEAK, 3)),
                        true),
                arguments("정확히 12만원인 경우",
                        new SelectedMenu(Map.of(Menu.BBQ_RIBS, 2, Menu.MUSHROOM_SOUP, 2)),
                        true),
                arguments("12만원 미만인 경우",
                        new SelectedMenu(Map.of(Menu.RED_WINE, 1, Menu.BBQ_RIBS, 1, Menu.TAPAS, 1)),
                        false)
        );
    }

    static Stream<Arguments> provideArgumentsForEventTest() {
        return Stream.of(
                arguments("1만원을 초과하는 경우",
                        new SelectedMenu(Map.of(Menu.TAPAS, 1, Menu.ICE_CREAM, 1)),
                        false),
                arguments("정확히 1만원인 경우",
                        new SelectedMenu(Map.of(Menu.ICE_CREAM, 2)),
                        false),
                arguments("1만원 미만인 경우",
                        new SelectedMenu(Map.of(Menu.MUSHROOM_SOUP, 1, Menu.ZERO_COKE, 1)),
                        true)
        );
    }
}