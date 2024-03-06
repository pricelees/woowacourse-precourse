package bridge.view.input;

import bridge.constants.ErrorMessage;

public class ConsoleInputValidator {
    private static final String NUMBER_REGEX = "\\d+";
    private static final int MIN_BRIDGE_SIZE = 3;
    private static final int MAX_BRIDGE_SIZE = 20;
    private static final String UP_AND_DOWN_REGEX = "[UD]";
    private static final String RESTART_AND_QUIT_REGEX = "[RQ]";

    public void validateBridgeSizeFormat(String bridgeSize) {
        if (hasNonNumber(bridgeSize)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BRIDGE_SIZE.getErrorMessgage());
        }
    }

    private boolean hasNonNumber(String input) {
        return !input.matches(NUMBER_REGEX);
    }

    public void validateBridgeSize(int bridgeSize) {
        if (bridgeSize > MAX_BRIDGE_SIZE || bridgeSize < MIN_BRIDGE_SIZE) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BRIDGE_SIZE.getErrorMessgage());
        }
    }


    public void validateLocationToMove(String location) {
        if (isInvalidLocationInput(location)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOCATION.getErrorMessgage());
        }
    }

    private boolean isInvalidLocationInput(String location) {
        return !location.matches(UP_AND_DOWN_REGEX);
    }

    public void validateRestartOption(String option) {
        if (isInvalidOption(option)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_GAME_COMMAND.getErrorMessgage());
        }
    }

    private boolean isInvalidOption(String option) {
        return !option.matches(RESTART_AND_QUIT_REGEX);
    }
}
