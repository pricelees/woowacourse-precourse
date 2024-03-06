package christmas.domain;

import christmas.constants.Constants;
import christmas.constants.domain.DomainErrorMessage;
import christmas.constants.menu.Menu;
import christmas.constants.menu.MenuCategory;
import java.util.Map;
import java.util.stream.Collectors;

public record SelectedMenu(Map<Menu, Integer> menuAndCounts) {
    private static final String SPACE = " ";
    private static final String COUNT_TO_KOREAN = "개";

    public SelectedMenu {
        validateMenuAndCount(menuAndCounts);
    }

    public int getTotalAmountBeforeDiscount() {
        return menuAndCounts.keySet().stream()
                .mapToInt(menu -> menuAndCounts.get(menu) * menu.getPrice())
                .sum();
    }

    public int getDessertCounts() {
        return menuAndCounts.keySet().stream()
                .filter(menu -> menu.isSameCategory(MenuCategory.DESSERT))
                .mapToInt(menuAndCounts::get)
                .sum();
    }

    public int getMainMenuCounts() {
        return menuAndCounts.keySet().stream()
                .filter(menu -> menu.isSameCategory(MenuCategory.MAIN))
                .mapToInt(menuAndCounts::get)
                .sum();
    }

    private void validateMenuAndCount(final Map<Menu, Integer> menuAndCounts) {
        if (isOverMaxMenuCount(menuAndCounts)) {
            throw new IllegalArgumentException(DomainErrorMessage.OVER_MAX_MENU_COUNT_ERROR.getErrorMessage());
        }
        if (hasOnlyDrink(menuAndCounts)) {
            throw new IllegalArgumentException(DomainErrorMessage.ORDER_ONLY_DRINK_ERROR.getErrorMessage());
        }
    }

    private boolean isOverMaxMenuCount(final Map<Menu, Integer> menuAndCounts) {
        return menuAndCounts.values().stream()
                .mapToInt(Integer::intValue)
                .sum() > Constants.MAXIMUM_TOTAL_MENU_COUNT;
    }

    private boolean hasOnlyDrink(final Map<Menu, Integer> menuAndCounts) {
        return menuAndCounts.keySet()
                .stream()
                .allMatch(menu -> menu.isSameCategory(MenuCategory.DRINK));
    }

    @Override
    public String toString() {
        return menuAndCounts.keySet().stream()
                .map(menu -> menu.toString() + SPACE + menuAndCounts.get(menu) + COUNT_TO_KOREAN)
                .collect(Collectors.joining(Constants.LINE_SEPARATOR));
    }
}
