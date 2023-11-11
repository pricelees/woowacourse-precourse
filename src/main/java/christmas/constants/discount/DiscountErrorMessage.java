package christmas.constants.discount;

public enum DiscountErrorMessage {
    YEAR_AND_MONTH_ERROR("[ERROR] 이벤트는 2023년 12월에만 진행됩니다."),
    NOT_BEFORE_CHRISTMAS_ERROR("[ERROR] 크리스마스 디데이 할인은 1일부터 25일까지만 진행합니다."),
    NOT_SPECIAL_DAY_ERROR("[ERROR] 특별 할인이 적용되는 날이 아닙니다."),
    NOT_WEEKDAY_ERROR("[ERROR] 해당 이벤트는 평일에만 진행됩니다."),
    NOT_WEEKEND_ERROR("[ERROR] 해당 이벤트는 주말에만 진행됩니다."),
    CANNOT_RECEIVE_CHAMPAGNE("[ERROR] 샴페인 증정을 위한 최소 주문금액은 12만원 입니다.");

    private final String errorMessage;

    DiscountErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
