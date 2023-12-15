package menu.util;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import menu.constant.MenuContainer;
import menu.domain.Coach;

public final class CoachMenuSelector {
    private static final int FIRST_MENU_INDEX = 0;
    private CoachMenuSelector() {
    }

    public static String selectMenu(MenuContainer category, Coach coach) {
        while (true) {
            String menuName = Randoms.shuffle(category.getMenus()).get(FIRST_MENU_INDEX);
            if (coach.cantEat(menuName)) {
                continue;
            }
            return menuName;
        }
    }
}
