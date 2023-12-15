package menu.constant;

public enum ExceptionMessage {
    INVALID_NAMES_FORMAT("입력 형식이 잘못되었습니다."),
    HAS_INVALID_COACH_NAME_LENGTH("코치의 이름은 2글자부터 4글자까지 가능합니다."),
    HAS_INVALID_COACH_COUNT("코치는 최소 2명 이상 입력해야 합니다."),
    OVER_MAXIMUM_EXCEPT_MENU_COUNT("먹지 않을 메뉴는 최대 2개까지 선택 가능합니다."),
    NOT_EXIST_MENU("존재하지 않는 메뉴입니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return ERROR_PREFIX + message;
    }
}
