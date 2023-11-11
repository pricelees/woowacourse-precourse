package christmas.discount;

import christmas.constants.discount.DiscountErrorMessage;
import christmas.constants.time.EventTime;
import christmas.domain.SelectedMenu;
import java.time.LocalDateTime;

public interface DiscountStrategy {
    int getDiscountAmount(LocalDateTime date, SelectedMenu selectedMenu);

    void validateDate(LocalDateTime date);

    String getTypeName();

    default void validateYearAndMonth(LocalDateTime date) {
        if (date.getYear() != EventTime.YEAR || date.getMonth() != EventTime.MONTH) {
            throw new IllegalArgumentException(DiscountErrorMessage.YEAR_AND_MONTH_ERROR);
        }
    }
}
