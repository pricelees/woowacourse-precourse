package menu.domain;

import java.util.List;

public class WeeklyMenu {
    private final List<DailyMenu> menuOfThisWeek;

    public WeeklyMenu(List<DailyMenu> menuOfThisWeek) {
        this.menuOfThisWeek = menuOfThisWeek;
    }

    public List<String> getWeeklyMenu(Coach coach) {
        return menuOfThisWeek.stream()
                .map(dailyMenu -> dailyMenu.findMenuOf(coach))
                .toList();
    }

    public List<String> getWeeklyMenuCategory() {
        return menuOfThisWeek.stream()
                .map(DailyMenu::getMenuCategory)
                .toList();
    }

    public List<String> getLunchDayOfThisWeek() {
        return menuOfThisWeek.stream()
                .map(DailyMenu::getDayOfMonth)
                .toList();
    }
}
