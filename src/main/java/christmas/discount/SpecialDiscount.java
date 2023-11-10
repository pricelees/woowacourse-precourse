package christmas.discount;

import christmas.constants.time.EventTime;
import christmas.domain.SelectedMenu;
import java.time.LocalDateTime;

public class SpecialDiscount implements DiscountStrategy {
    private static final String DISCOUNT_TYPE = "특별 할인";
    private static final int DISCOUNT_AMOUNT = 1000;

    @Override
    public int getDiscountAmount(LocalDateTime date, SelectedMenu selectedMenu) {
        validateDate(date);
        return DISCOUNT_AMOUNT;
    }

    @Override
    public void validateDate(LocalDateTime date) {
        validateYearAndMonth(date);
        if (EventTime.SPECIAL_DISCOUNT_DAYS.contains(date.getDayOfMonth())) {
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String getTypeName() {
        return DISCOUNT_TYPE;
    }
}
