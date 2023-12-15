package menu.dto;

import java.util.List;

public record WeeklyCoachMenuResponse(String coachName, List<String> menuList) {
}
