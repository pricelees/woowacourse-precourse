package christmas.discount;

import christmas.constants.discount.DiscountErrorMessage;
import christmas.domain.Customer;
import java.time.LocalDateTime;

public class ChampagneDiscount implements DiscountStrategy {
    private static final String DISCOUNT_TYPE = "증정 이벤트";
    private static final int ONE_CHAMPAGNE_PRICE = 25_000;

    @Override
    public int getDiscountAmount(Customer customer) {
        validateDate(customer.dateToVisit());
        validateOrderAmount(customer);
        return -ONE_CHAMPAGNE_PRICE;
    }

    @Override
    public void validateDate(LocalDateTime date) {
        validateYearAndMonth(date);
    }

    @Override
    public String getTypeName() {
        return DISCOUNT_TYPE;
    }

    private void validateOrderAmount(Customer customer) {
        if (customer.canReceiveFreeChampagne()) {
            return;
        }
        throw new IllegalArgumentException(DiscountErrorMessage.CANNOT_RECEIVE_CHAMPAGNE);
    }
}
