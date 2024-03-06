package christmas.domain;

import static christmas.TestConstants.CHRISTMAS;
import static christmas.TestConstants.HOUR_17;
import static christmas.TestConstants.MINUTE_00;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constants.menu.Menu;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CustomerTest {
    DateToVisit dateToVisit;

    @BeforeEach
    void setUp() {
        dateToVisit = new DateToVisit(LocalDateTime.of(
                2023, 12, CHRISTMAS, HOUR_17, MINUTE_00
        ));
    }

    @DisplayName("구매 금액에 따른 샴페인 증정 여부 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideArgumentsForChampagneTest")
    void canReceiveFreeChampagne(String testName, SelectedMenu selectedMenu, boolean expectedResult) {
        Customer customer = new Customer(dateToVisit, selectedMenu);

        assertThat(customer.canReceiveFreeChampagne()).isEqualTo(expectedResult);
    }

    @DisplayName("구매 금액에 따른 이벤트 참여 불가 여부 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideArgumentsForEventTest")
    void cannotParticipateEvent(String testName, SelectedMenu selectedMenu, boolean expectedResult) {
        Customer customer = new Customer(dateToVisit, selectedMenu);

        assertThat(customer.cannotParticipateEvent()).isEqualTo(expectedResult);
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