package christmas.view.inputview;

import camp.nextstep.edu.missionutils.Console;
import christmas.constants.time.EventTime;
import christmas.constants.view.DateInputViewConstants;
import java.time.LocalDateTime;

public class DateInputView {
    public LocalDateTime receiveVisitDate() {
        System.out.println(DateInputViewConstants.VISIT_DAY_REQUEST_MESSAGE);
        String input = Console.readLine();
        validateDateFormat(input);

        int day = Integer.parseInt(input);
        validateRange(day);

        return LocalDateTime.of(
                EventTime.YEAR, EventTime.MONTH, day,
                EventTime.HOUR, EventTime.MINUTE, EventTime.SECOND);
    }

    private void validateDateFormat(String input) {
        if (isInvalidFormat(input)) {
            throw new IllegalArgumentException(DateInputViewConstants.ERROR_MESSAGE);
        }
    }

    private boolean isInvalidFormat(String input) {
        return !input.matches(DateInputViewConstants.ONLY_CONTAINS_NUMBER_REGEX);
    }

    private void validateRange(int day) {
        if (day < DateInputViewConstants.MIN_DAY_OF_MONTH ||
                day > DateInputViewConstants.MAX_DAY_OF_MONTH) {
            throw new IllegalArgumentException(DateInputViewConstants.ERROR_MESSAGE);
        }
    }
}
