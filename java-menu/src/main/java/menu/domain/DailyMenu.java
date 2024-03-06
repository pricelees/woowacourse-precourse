package menu.domain;

import java.util.Map;
import menu.constant.LunchDay;
import menu.constant.MenuContainer;

public class DailyMenu {
    private final LunchDay lunchDay;
    private final MenuContainer menuContainer;
    private final Map<Coach, String> recommendedMenu;

    public DailyMenu(LunchDay lunchDay, MenuContainer menuContainer, Map<Coach, String> recommendedMenu) {
        this.lunchDay = lunchDay;
        this.menuContainer = menuContainer;
        this.recommendedMenu = recommendedMenu;
    }

    public String findMenuOf(Coach coach) {
        return recommendedMenu.get(coach);
    }

    public String getDayOfMonth() {
        return lunchDay.getDayOfWeek();
    }

    public String getMenuCategory() {
        return menuContainer.getCategoryName();
    }
}
