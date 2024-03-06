package menu.service;

import java.util.List;
import menu.domain.Coach;
import menu.dto.CoachRequest;

public class CoachService {
    public List<Coach> loadAllCoachInfo(List<CoachRequest> coachRequests) {
        return coachRequests.stream()
                .map(this::loadCoachInfo)
                .toList();
    }

    private Coach loadCoachInfo(CoachRequest coachRequest) {
        return Coach.valueOf(coachRequest.coachName(), coachRequest.menusCantEat());
    }
}
