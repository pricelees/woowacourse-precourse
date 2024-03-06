package menu.dto;

import java.util.List;
import menu.constant.ExceptionMessage;

public record CoachNamesRequest(List<String> coachNames){
    private static final int MIN_COACH_NAME_LENGTH = 2;
    private static final int MAX_COACH_NAME_LENGTH = 4;
    private static final int MIN_COACH_COUNT = 2;
    private static final int MAX_COACH_COUNT = 5;

    public CoachNamesRequest {
        coachNames.forEach(this::validateNameLength);
        validateSize(coachNames);
    }

    private void validateSize(List<String> coachNames) {
        if (coachNames.size() < MIN_COACH_COUNT || coachNames.size() > MAX_COACH_COUNT) {
            throw new IllegalArgumentException(ExceptionMessage.HAS_INVALID_COACH_COUNT.getMessage());
        }
    }

    private void validateNameLength(String coachName) {
        int nameLength = coachName.length();
        if (nameLength > MAX_COACH_NAME_LENGTH || nameLength < MIN_COACH_NAME_LENGTH) {
            throw new IllegalArgumentException(ExceptionMessage.HAS_INVALID_COACH_NAME_LENGTH.getMessage());
        }
    }
}
