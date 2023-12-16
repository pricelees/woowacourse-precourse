package oncall.view.input;

import oncall.config.ErrorMessage;

public final class ConsoleInputValidator {
    private static final String NAMES_INPUT_REGEX = "([가-힣]+)(,([가-힣]+))*";
    private static final String DATE_INPUT_REGEX = "(([0-9]+)(,)([월화수목금토일]))";
    private static ConsoleInputValidator instance;

    private ConsoleInputValidator() {
    }

    public static ConsoleInputValidator getInstance() {
        if (instance == null) {
            instance = new ConsoleInputValidator();
        }
        return instance;
    }

    public void validateDateFormat(String input) {
        if (input.matches(DATE_INPUT_REGEX)) {
            return;
        }
        throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
    }

    public void validateWorkerNameFormat(String input) {
        if (input.matches(NAMES_INPUT_REGEX)) {
            return;
        }
        throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
    }
}
