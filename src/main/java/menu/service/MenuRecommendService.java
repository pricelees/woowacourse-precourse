package menu.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import menu.constant.LunchDay;
import menu.constant.MenuContainer;
import menu.domain.Coach;
import menu.domain.DailyMenu;
import menu.domain.WeeklyMenu;
import menu.util.CoachMenuSelector;

public class MenuRecommendService {
    private static final int FIRST_MENU_CATEGORY_NUMBER = 1;
    private static final int LAST_MENU_CATEGORY_NUMBER = 5;
    private static final int MAX_CATEGORY_COUNT = 2;

    public WeeklyMenu recommendWeeklyMenu(List<Coach> coaches) {
        List<LunchDay> lunchDays = Arrays.asList(LunchDay.values());
        EnumMap<MenuContainer, Integer> categoriesCount = new EnumMap<>(MenuContainer.class);

        return new WeeklyMenu(lunchDays.stream()
                .map(dayOfWeek -> {
                    MenuContainer menuContainer = selectRandomCategory(categoriesCount);
                    categoriesCount.merge(menuContainer, 1, Integer::sum);
                    return recommendDailyMenu(menuContainer, dayOfWeek, coaches);
                })
                .toList());
    }

    private DailyMenu recommendDailyMenu(MenuContainer menuContainer, LunchDay dayOfWeek, List<Coach> coaches) {
        Map<Coach, String> recommendedMenus = new LinkedHashMap<>();
        coaches.forEach(coach -> {
            String recommendedMenu = recommendMenuByCoach(menuContainer, coach);
            recommendedMenus.put(coach, recommendedMenu);
            coach.addMenuCantEat(recommendedMenu);
        });

        return new DailyMenu(dayOfWeek, menuContainer, recommendedMenus);
    }

    private String recommendMenuByCoach(MenuContainer menuContainer, Coach coach) {
        return CoachMenuSelector.selectMenu(menuContainer, coach);
    }

    private MenuContainer selectRandomCategory(Map<MenuContainer, Integer> categoriesCount) {
        while (true) {
            MenuContainer menuContainer = MenuContainer.getCategoryFromNumber(Randoms.pickNumberInRange(
                    FIRST_MENU_CATEGORY_NUMBER,
                    LAST_MENU_CATEGORY_NUMBER
            ));

            if (categoriesCount.get(menuContainer) == null || categoriesCount.get(menuContainer) < MAX_CATEGORY_COUNT) {
                return menuContainer;
            }
        }
    }
}
