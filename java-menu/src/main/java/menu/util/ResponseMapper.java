package menu.util;

import java.util.List;
import menu.domain.Coach;
import menu.domain.WeeklyMenu;
import menu.dto.WeeklyCoachMenuResponse;
import menu.dto.WeeklyLunchDayResponse;
import menu.dto.WeeklyMenuResponse;

public class ResponseMapper {
    private ResponseMapper() {
    }

    public static WeeklyMenuResponse mapWeeklyMenuResponse(List<Coach> coaches, WeeklyMenu weeklyMenu) {
        return new WeeklyMenuResponse(coaches.stream()
                .map(coach -> new WeeklyCoachMenuResponse(coach.getName(), weeklyMenu.getWeeklyMenu(coach)))
                .toList());
    }

    public static WeeklyLunchDayResponse mapWeeklyLunchDayResponse(WeeklyMenu weeklyMenu) {
        return new WeeklyLunchDayResponse(
                weeklyMenu.getLunchDayOfThisWeek(),
                weeklyMenu.getWeeklyMenuCategory()
        );
    }
}
