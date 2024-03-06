package christmas.constants.view;

public enum OutputViewConstants {
    WELCOME_MESSAGE("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다."),
    PREVIEW_BENEFITS_MESSAGE("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"),
    ORDERED_MENU("<주문 메뉴>"),
    TOTAL_AMOUNT_BEFORE_DISCOUNT("<할인 전 총주문 금액>"),
    MENU_FOR_FREE("<증정 메뉴>"),
    BENEFITS_DESCRIPTION("<혜택 내역>"),
    TOTAL_BENEFITS_AMOUNT("<총혜택 금액>"),
    PRICE_AFTER_DISCOUNT("<할인 후 예상 결제 금액>"),
    EVENT_BADGE("<12월 이벤트 배지>");

    private final String message;

    OutputViewConstants(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
