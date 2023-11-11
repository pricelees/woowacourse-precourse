package christmas.constants.domain;

public enum DomainErrorMessage {
    OVER_MAX_MENU_COUNT_ERROR("[ERROR] 메뉴의 총 개수는 20개를 초과할 수 없습니다."),
    ORDER_ONLY_DRINK_ERROR("[ERROR] 음료만 주문할 수 없습니다."),
    INVALID_YEAR_ERROR("[ERROR] 이벤트는 2023년에 진행됩니다."),
    INVALID_MONTH_ERROR("[ERROR] 이벤트는 12월에만 진행됩니다."),
    NEGATIVE_AMOUNT_ERROR("[ERROR] 할인 후의 금액이 할인 전 금액보다 클 수 없습니다.");

    private final String errorMessage;

    DomainErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
