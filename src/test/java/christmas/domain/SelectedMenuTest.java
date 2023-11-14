package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import christmas.constants.menu.Menu;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SelectedMenuTest {
    SelectedMenu selectedMenu;

    @DisplayName("예외 발생 확인")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideInvalidMenu")
    void constructor_WithInvalidInput_ThrowsException(String testName, Map<Menu, Integer> menuAndCount) {
        assertThatThrownBy(() -> new SelectedMenu(menuAndCount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @BeforeEach
    void setUp() {
        // 모든 메뉴를 모두 1개씩 선택하고 테스트 진행
        Map<Menu, Integer> allMenu = new EnumMap<>(Menu.class);
        for (Menu menu : Menu.values()) {
            allMenu.put(menu, 1);
        }
        selectedMenu = new SelectedMenu(allMenu);
    }

    @DisplayName("모든 메뉴를 1개씩 선택했을 때, 정확한 총계를 계산하는지 확인")
    @Test
    void getTotalAmountBeforeDiscount_WithAllMenu() {
        int expectedAmount = Arrays.stream(Menu.values())
                .mapToInt(Menu::getPrice)
                .sum();

        assertThat(selectedMenu.getTotalAmountBeforeDiscount())
                .isEqualTo(expectedAmount);
    }

    @DisplayName("모든 메뉴를 1개씩 선택했을 때, 정확한 디저트 메뉴의 개수를 계산하는지 확인")
    @Test
    void getDessertCounts_WithAllMenu() {
        int expectedDessertCount = 2;
        assertThat(selectedMenu.getDessertCounts())
                .isEqualTo(expectedDessertCount);
    }

    @DisplayName("모든 메뉴를 1개씩 선택했을 때, 정확한 메인 메뉴의 개수를 계산하는지 확인")
    @Test
    void getMainMenuCounts_WithAllMenu() {
        int expectedMainCount = 4;
        assertThat(selectedMenu.getMainMenuCounts())
                .isEqualTo(expectedMainCount);
    }

    @DisplayName("모든 메뉴를 1개씩 선택했을 때, 메뉴 리스트를 정확히 반환하는지 확인")
    @Test
    void toString_WithAllMenu() {
        String[] expected = {"양송이수프 1개", "타파스 1개", "시저샐러드 1개",
                "티본스테이크 1개", "바비큐립 1개", "해산물파스타 1개", "크리스마스파스타 1개",
                "초코케이크 1개", "아이스크림 1개",
                "제로콜라 1개", "레드와인 1개", "샴페인 1개"};

        assertThat(selectedMenu.toString()).contains(expected);
    }

    static Stream<Arguments> provideInvalidMenu() {
        return Stream.of(
                arguments("메뉴의 총 개수가 20개 이상인 경우", Map.of(Menu.CAESAR_SALAD, 25)),
                arguments("음료만 주문하는 경우", Map.of(Menu.CHAMPAGNE, 1, Menu.ZERO_COKE, 1))
        );
    }
}