package christmas.discount;

import christmas.constants.Constants;
import christmas.domain.Customer;
import christmas.domain.DateToVisit;
import christmas.util.PriceFormatter;

public final class WeekdayDiscount implements DiscountStrategy {
    private static final WeekdayDiscount INSTANCE = new WeekdayDiscount();
    private static final String DISCOUNT_TYPE = "평일 할인";
    private static final int DISCOUNT_AMOUNT_PER_ONE_DESSERT = 2023;

    public static WeekdayDiscount getInstance() {
        return INSTANCE;
    }

    private WeekdayDiscount() {
    }

    @Override
    public int getDiscountAmount(final Customer customer) {
        final DateToVisit dateToVisit = customer.dateToVisit();
        if (dateToVisit.isWeekday()) {
            return -(customer.countDessert() * DISCOUNT_AMOUNT_PER_ONE_DESSERT);
        }

        return Constants.ZERO;
    }

    @Override
    public String getDescription(Customer customer) {
        return DISCOUNT_TYPE + Constants.COLON_WITH_SPACE + PriceFormatter.format(getDiscountAmount(customer));
    }
}
