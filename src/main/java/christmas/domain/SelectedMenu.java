package christmas.domain;

import christmas.constants.Constants;
import christmas.constants.menu.Menu;
import christmas.constants.menu.MenuCategory;
import java.util.Map;
import java.util.stream.Collectors;

public record SelectedMenu(Map<Menu, Integer> menuAndCounts) {
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
