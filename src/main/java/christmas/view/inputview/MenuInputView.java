package christmas.view.inputview;

import static christmas.constants.Constants.ONE;
import static christmas.constants.Constants.ZERO;
import static christmas.constants.view.MenuInputViewConstants.COMMA;
import static christmas.constants.view.MenuInputViewConstants.DASH;

import camp.nextstep.edu.missionutils.Console;
import christmas.constants.Constants;
import christmas.constants.menu.Menu;
import christmas.constants.menu.MenuCategory;
import christmas.constants.view.MenuInputViewConstants;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MenuInputView {
    public Map<Menu, Integer> receiveMenuAndCounts() {
        System.out.println(MenuInputViewConstants.MENU_AND_COUNT_REQUEST_MESSAGE);
        String input = Console.readLine();
        validateInputFormat(input);
        validateMenuName(input);
        validateMenuCount(input);

        return createMapFromInput(input);
    }

    private void validateInputFormat(String input) {
        if (isInvalidInputFormat(input)) {
            throw new IllegalArgumentException(MenuInputViewConstants.ERROR_MESSAGE);
        }
    }

    private boolean isInvalidInputFormat(String input) {
        return !input.matches(MenuInputViewConstants.MENU_INPUT_FORMAT_REGEX);
    }

    private void validateMenuName(String input) {
        List<String> menuName = Arrays.stream(input.split(COMMA))
                .map(nameAndCount -> nameAndCount.split(DASH)[ZERO])
                .toList();

        if (isNotExistMenu(menuName)) {
            throw new IllegalArgumentException(MenuInputViewConstants.ERROR_MESSAGE);
        }
        if (hasDuplicateMenu(menuName)) {
            throw new IllegalArgumentException(MenuInputViewConstants.ERROR_MESSAGE);
        }
        if (isContainsOnlyDrink(menuName)) {
            throw new IllegalArgumentException(MenuInputViewConstants.ERROR_MESSAGE);
        }
    }

    private boolean isNotExistMenu(List<String> menuName) {
        try {
            menuName.forEach(Menu::findMenuFromKoreanName);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    private boolean hasDuplicateMenu(List<String> menuName) {
        Set<String> distinctMenuName = new HashSet<>(menuName);
        return distinctMenuName.size() != menuName.size();
    }

    private boolean isContainsOnlyDrink(List<String> menuName) {
        return menuName.stream()
                .map(Menu::findMenuFromKoreanName)
                .allMatch(menu -> menu.isSameCategory(MenuCategory.DRINK));
    }

    private void validateMenuCount(String input) {
        List<String> menuCount = Arrays.stream(input.split(COMMA))
                .map(nameAndCount -> nameAndCount.split(DASH)[ONE])
                .toList();

        if (isInvalidMenuCountFormat(menuCount)) {
            throw new IllegalArgumentException(MenuInputViewConstants.ERROR_MESSAGE);
        }
        if (isInvalidEachMenuCountRange(menuCount)) {
            throw new IllegalArgumentException(MenuInputViewConstants.ERROR_MESSAGE);
        }
        if (isInvalidTotalMenuCountRange(menuCount)) {
            throw new IllegalArgumentException(MenuInputViewConstants.ERROR_MESSAGE);
        }
    }

    private boolean isInvalidMenuCountFormat(List<String> menuCount) {
        return menuCount.stream()
                .anyMatch(this::isNotContainsOnlyNumber);
    }

    private boolean isNotContainsOnlyNumber(String count) {
        return !count.matches(MenuInputViewConstants.ONLY_CONTAINS_NUMBER_REGEX);
    }

    private boolean isInvalidEachMenuCountRange(List<String> menuCount) {
        return menuCount.stream()
                .mapToInt(Integer::parseInt)
                .anyMatch(count -> count < ONE);
    }

    private boolean isInvalidTotalMenuCountRange(List<String> menuCount) {
        return Constants.MAXIMUM_TOTAL_MENU_COUNT <
                menuCount.stream()
                        .mapToInt(Integer::parseInt)
                        .sum();
    }

    private Map<Menu, Integer> createMapFromInput(String input) {
        Map<Menu, Integer> result = new EnumMap<>(Menu.class);

        Arrays.stream(input.split(COMMA))
                .forEach(menuAndCount -> {
                    Menu menu = Menu.findMenuFromKoreanName(menuAndCount.split(DASH)[ZERO]);
                    int count = Integer.parseInt(menuAndCount.split(DASH)[ONE]);
                    result.put(menu, count);
                });

        return Collections.unmodifiableMap(result);
    }
}
