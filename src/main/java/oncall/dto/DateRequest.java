package oncall.dto;

import oncall.constant.ErrorMessage;

public record DateRequest(int month, String dayOfWeek) {
    private static final String ALL_DAYOFWEEK = "월화수목금토일";
    private static final int MIN_INTEGER_MONTH = 1;
    private static final int MAX_INTEGER_MONTH = 12;

    public DateRequest {
        validateMonth(month);
        validateDayOfWeek(dayOfWeek);
    }

    private void validateMonth(int month) {
        if (month > MAX_INTEGER_MONTH || month < MIN_INTEGER_MONTH) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }

    private void validateDayOfWeek(String dayOfWeek) {
        if (!ALL_DAYOFWEEK.contains(dayOfWeek)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }
}
