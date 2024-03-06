package menu.view.input;

import menu.constant.ExceptionMessage;

public final class ConsoleInputValidator {
    private static final String COACH_NAME_REGEX = "([가-힣]+)(,[가-힣]+){1,4}";
    private static final String MENU_CANT_EAT_REGEX = "(([가-힣]+)?)|([가-힣]+,[가-힣]+)";
    private static ConsoleInputValidator instance;
    private ConsoleInputValidator() {
    }

    public static ConsoleInputValidator getInstance() {
        if (instance == null) {
            instance = new ConsoleInputValidator();
        }
        return instance;
    }

    public void validateCoachNames(String input) {
        if (input.matches(COACH_NAME_REGEX)) {
            return;
        }
        throw new IllegalArgumentException(ExceptionMessage.INVALID_NAMES_FORMAT.getMessage());
    }

    public void validateMenusCantEat(String input) {
        if (input.matches(MENU_CANT_EAT_REGEX)) {
            return;
        }
        throw new IllegalArgumentException(ExceptionMessage.INVALID_NAMES_FORMAT.getMessage());
    }
}
