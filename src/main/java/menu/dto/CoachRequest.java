package menu.dto;

import java.util.List;
import menu.constant.ExceptionMessage;
import menu.constant.MenuContainer;

public record CoachRequest(String coachName, List<String> menusCantEat) {
    private static final int MAX_CANT_EAT_MENU_COUNT = 2;
    public CoachRequest {
        validateMenu(menusCantEat);
    }

    private void validateMenu(List<String> menusCantEat) {
        if (menusCantEat.size() > MAX_CANT_EAT_MENU_COUNT) {
            throw new IllegalArgumentException(ExceptionMessage.OVER_MAXIMUM_EXCEPT_MENU_COUNT.getMessage());
        }
        if (hasNonExistMenu(menusCantEat)) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_EXIST_MENU.getMessage());
        }
    }

    private boolean hasNonExistMenu(List<String> menusCantEat) {
        return menusCantEat.stream()
                .anyMatch(MenuContainer::isNotExistMenu);
    }
}
