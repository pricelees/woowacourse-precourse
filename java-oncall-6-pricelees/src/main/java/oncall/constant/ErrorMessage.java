package oncall.constant;

public enum ErrorMessage {
    INVALID_INPUT("유효하지 않은 입력 값입니다. 다시 입력해 주세요.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}
