package christmas.view.inputview;

import camp.nextstep.edu.missionutils.Console;
import christmas.constants.time.EventTime;
import christmas.constants.view.DateInputViewConstants;
import java.time.DateTimeException;
import java.time.LocalDateTime;

public class DateInputView {
    public LocalDateTime receiveVisitDate() {
        System.out.println(DateInputViewConstants.VISIT_DAY_REQUEST_MESSAGE);
        String input = Console.readLine();
        validateDateFormat(input);

        return parseInputWithValidation(input);
    }

    // 날짜가 1일부터 31일까지인지에 대한 검증
    // input이 1부터 31까지의 값이 아니면 LocalDateTime 객체를 만들 때 DateTimeException이 발생
    private LocalDateTime parseInputWithValidation(String input) {
        try {
            return LocalDateTime.of(
                    EventTime.YEAR, EventTime.MONTH, Integer.parseInt(input),
                    EventTime.HOUR, EventTime.MINUTE, EventTime.SECOND);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(DateInputViewConstants.ERROR_MESSAGE);
        }
    }

    private void validateDateFormat(String input) {
        if (isInvalidFormat(input)) {
            throw new IllegalArgumentException(DateInputViewConstants.ERROR_MESSAGE);
        }
    }

    private boolean isInvalidFormat(String input) {
        return !input.matches(DateInputViewConstants.ONLY_CONTAINS_NUMBER_REGEX);
    }
}
