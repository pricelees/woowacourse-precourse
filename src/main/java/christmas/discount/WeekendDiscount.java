package christmas.discount;

import christmas.constants.Constants;
import christmas.domain.Customer;
import christmas.domain.DateToVisit;
import christmas.util.PriceFormatter;

public final class WeekendDiscount implements DiscountStrategy {
    private static final WeekendDiscount INSTANCE = new WeekendDiscount();
    private static final String DISCOUNT_TYPE = "주말 할인";
    private static final int DISCOUNT_AMOUNT_PER_ONE_MAIN_MENU = 2023;

    public static WeekendDiscount getInstance() {
        return INSTANCE;
    }

    private WeekendDiscount() {
    }

    @Override
    public int getDiscountAmount(final Customer customer) {
        final DateToVisit dateToVisit = customer.dateToVisit();
        if (dateToVisit.isWeekend()) {
            return -(customer.countMainMenu() * DISCOUNT_AMOUNT_PER_ONE_MAIN_MENU);
        }

        return Constants.ZERO;
    }

    @Override
    public String getDescription(final Customer customer) {
        return DISCOUNT_TYPE + Constants.COLON_WITH_SPACE + PriceFormatter.format(getDiscountAmount(customer));
    }
}
