package christmas.discount;

import christmas.constants.time.EventTime;
import christmas.domain.SelectedMenu;
import java.time.LocalDateTime;

public class ChristmasDiscount implements DiscountStrategy {
    private static final String DISCOUNT_TYPE = "크리스마스 디데이 할인";
    private static final LocalDateTime EVENT_START_DATE = LocalDateTime.of(
            EventTime.YEAR, EventTime.MONTH, EventTime.DAY_OF_MONTH_EVENTS_START,
            EventTime.HOUR, EventTime.MINUTE, EventTime.SECOND);
    private static final int DEFAULT_DISCOUNT_AMOUNT = 1000;
    private static final int DISCOUNT_AMOUNT_PER_DAY = 100;

    @Override
    public int getDiscountAmount(LocalDateTime date, SelectedMenu selectedMenu) {
        validateDate(date);

        int totalDay = date.getDayOfMonth() - EVENT_START_DATE.getDayOfMonth();
        return DEFAULT_DISCOUNT_AMOUNT + (DISCOUNT_AMOUNT_PER_DAY * totalDay);
    }

    @Override
    public void validateDate(LocalDateTime date) {
        validateYearAndMonth(date);
        if (date.getDayOfMonth() > EventTime.DAY_OF_MONTH_CHRISTMAS) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getTypeName() {
        return DISCOUNT_TYPE;
    }
}
