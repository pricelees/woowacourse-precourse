package christmas.domain;

import christmas.constants.Constants;
import christmas.constants.menu.Menu;
import christmas.constants.menu.MenuCategory;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public record SelectedMenu(Map<Menu, Integer> menuAndCounts) {
    private static final String MenuFormat = "%s %d개";

    public int getTotalAmountBeforeDiscount() {
        return menuAndCounts.keySet()
                .stream()
                .mapToInt(menu -> menuAndCounts.get(menu) * menu.getPrice())
                .sum();
    }

    public int getDessertCounts() {
        MenuCategory dessert = MenuCategory.DESSERT;
        return (int) Arrays.stream(Menu.values())
                .filter(menu -> menu.isSameCategory(dessert))
                .count();
    }

    public int getMainMenuCounts() {
        MenuCategory mainMenu = MenuCategory.MAIN;
        return (int) Arrays.stream(Menu.values())
                .filter(menu -> menu.isSameCategory(mainMenu))
                .count();
    }

    @Override
    public String toString() {
        return menuAndCounts.keySet()
                .stream()
                .map(menu -> String.format(MenuFormat, menu.toString(), menuAndCounts.get(menu)))
                .collect(Collectors.joining(Constants.LINE_SEPARATOR));
    }
}
