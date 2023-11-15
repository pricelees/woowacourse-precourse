package christmas.discount;

import christmas.constants.Constants;
import christmas.domain.Customer;
import christmas.domain.DateToVisit;
import christmas.util.PriceFormatter;

public final class SpecialDiscount implements DiscountStrategy {
    private static final SpecialDiscount INSTANCE = new SpecialDiscount();
    private static final String DISCOUNT_TYPE = "특별 할인";
    private static final int DISCOUNT_AMOUNT = 1000;

    public static SpecialDiscount getInstance() {
        return INSTANCE;
    }

    private SpecialDiscount() {
    }

    @Override
    public int getDiscountAmount(final Customer customer) {
        final DateToVisit dateToVisit = customer.dateToVisit();
        if (dateToVisit.isSpecialDay()) {
            return -DISCOUNT_AMOUNT;
        }

        return Constants.ZERO;
    }

    @Override
    public String getDescription(final Customer customer) {
        return DISCOUNT_TYPE + Constants.COLON_WITH_SPACE + PriceFormatter.format(getDiscountAmount(customer));
    }
}
