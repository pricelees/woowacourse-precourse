package menu.dto;

import java.util.List;
import menu.constant.LunchDay;

public record WeeklyLunchDayResponse(List<String> lunchDays, List<String> menuCategories) {
}
