package christmas.discount;

import christmas.constants.discount.DiscountErrorMessage;
import christmas.constants.time.EventTime;
import christmas.domain.Customer;
import java.time.LocalDateTime;

public class WeekdayDiscount implements DiscountStrategy {
    private static final String DISCOUNT_TYPE = "평일 할인";
    private static final int DISCOUNT_AMOUNT_PER_ONE_DESSERT = 2023;

    @Override
    public int getDiscountAmount(Customer customer) {
        validateDate(customer.dateToVisit());

        return -(customer.countDessert() * DISCOUNT_AMOUNT_PER_ONE_DESSERT);
    }

    @Override
    public void validateDate(LocalDateTime date) {
        validateYearAndMonth(date);
        if (EventTime.WEEKDAYS.contains(date.getDayOfWeek())) {
            return;
        }
        throw new IllegalArgumentException(DiscountErrorMessage.NOT_WEEKDAY_ERROR.getErrorMessage());
    }

    @Override
    public String getTypeName() {
        return DISCOUNT_TYPE;
    }
}
