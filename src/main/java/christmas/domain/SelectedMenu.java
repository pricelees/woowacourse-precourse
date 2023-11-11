package christmas.domain;

import christmas.constants.Constants;
import christmas.constants.menu.Menu;
import christmas.constants.menu.MenuCategory;
import java.util.Map;
import java.util.stream.Collectors;

public record SelectedMenu(Map<Menu, Integer> menuAndCounts) {
    public SelectedMenu {
        validateMenuAndCount(menuAndCounts);
    }

    public int getTotalAmountBeforeDiscount() {
        return menuAndCounts.keySet()
                .stream()
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

    private void validateMenuAndCount(Map<Menu, Integer> menuAndCounts) {
        if (isOverMaxMenuCount(menuAndCounts)) {
            throw new IllegalArgumentException();
        }
        if (hasOnlyDrink(menuAndCounts)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isOverMaxMenuCount(Map<Menu, Integer> menuAndCounts) {
        return menuAndCounts.values().stream()
                .mapToInt(Integer::intValue)
                .sum() > Constants.MAXIMUM_TOTAL_MENU_COUNT;
    }

    private boolean hasOnlyDrink(Map<Menu, Integer> menuAndCounts) {
        return menuAndCounts.keySet()
                .stream()
                .allMatch(menu -> menu.isSameCategory(MenuCategory.DRINK));
    }

    @Override
    public String toString() {
        return menuAndCounts.keySet()
                .stream()
                .map(menu -> menu.toString()
                        + Constants.COLON_WITH_SPACE
                        + menuAndCounts.get(menu))
                .collect(Collectors.joining(Constants.LINE_SEPARATOR));
    }
}
