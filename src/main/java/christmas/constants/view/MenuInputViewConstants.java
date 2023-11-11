package christmas.constants.view;

public class MenuInputViewConstants {
    public static final String MENU_AND_COUNT_REQUEST_MESSAGE =
            "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    public static final String MENU_INPUT_FORMAT_REGEX = "([가-힣]+-([0-9]+))(,[가-힣]+-([0-9]+))*";
    public static final String ONLY_CONTAINS_NUMBER_REGEX = "[0-9]+";
    public static final String ERROR_MESSAGE = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.";
    public static final String COMMA = ",";
    public static final String DASH = "-";
}
