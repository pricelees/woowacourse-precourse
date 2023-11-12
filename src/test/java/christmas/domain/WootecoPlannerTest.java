package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constants.menu.Menu;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WootecoPlannerTest {
    // 모든 메뉴를 1개씩 선택하고 테스트 진행
    SelectedMenu selectedMenu;

    @BeforeEach
    void setUp() {
        Map<Menu, Integer> allMenu = new EnumMap<>(Menu.class);
        for (Menu menu : Menu.values()) {
            allMenu.put(menu, 1);
        }
        selectedMenu = new SelectedMenu(allMenu);
    }

    @DisplayName("할인 내역을 정확히 반환하는지 확인")
    @ParameterizedTest(name = "12월 {0}일")
    @MethodSource("provideArguments")
    void showDiscountDescription(int dayOfMonthToVisit, String[] expectedDiscountDescription) {
        WootecoPlanner wootecoPlanner = WootecoPlanner.valueOf(new Customer(
                LocalDateTime.of(2023, 12, dayOfMonthToVisit, 0, 0),
                selectedMenu
        ));

        assertThat(wootecoPlanner.showDiscountDescription()).contains(expectedDiscountDescription);
    }

    static Stream<Arguments> provideArguments() {
        return Stream.of(
                arguments(1, new String[]{"크리스마스 디데이 할인: -1,000원", "주말 할인: -8,092원", "증정 이벤트: -25,000원"}),
                arguments(3, new String[]{"크리스마스 디데이 할인: -1,200원", "평일 할인: -4,046원",
                        "특별 할인: -1,000원", "증정 이벤트: -25,000원"}),
                arguments(5, new String[]{"크리스마스 디데이 할인: -1,400원", "평일 할인: -4,046원", "증정 이벤트: -25,000원"}),
                arguments(9, new String[]{"크리스마스 디데이 할인: -1,800원", "주말 할인: -8,092원", "증정 이벤트: -25,000원"}),
                arguments(25, new String[]{"크리스마스 디데이 할인: -3,400원", "평일 할인: -4,046원"
                        , "특별 할인: -1,000원", "증정 이벤트: -25,000원"}),
                arguments(30, new String[]{"주말 할인: -8,092원", "증정 이벤트: -25,000원"}),
                arguments(31, new String[]{"평일 할인: -4,046원", "특별 할인: -1,000원", "증정 이벤트: -25,000원"})
        );
    }
}