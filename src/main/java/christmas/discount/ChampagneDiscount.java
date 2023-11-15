package christmas.discount;

import christmas.constants.Constants;
import christmas.domain.Customer;
import christmas.util.PriceFormatter;

public final class ChampagneDiscount implements DiscountStrategy {
    private static final ChampagneDiscount INSTANCE = new ChampagneDiscount();
    private static final String DISCOUNT_TYPE = "증정 이벤트";
    private static final int ONE_CHAMPAGNE_PRICE = 25_000;

    private ChampagneDiscount() {
    }

    public static ChampagneDiscount getInstance() {
        return INSTANCE;
    }

    @Override
    public int getDiscountAmount(final Customer customer) {
        if (customer.canReceiveFreeChampagne()) {
            return -ONE_CHAMPAGNE_PRICE;
        }
        return Constants.ZERO;
    }

    @Override
    public String getDescription(final Customer customer) {
        return DISCOUNT_TYPE + Constants.COLON_WITH_SPACE + PriceFormatter.format(getDiscountAmount(customer));
    }
}
