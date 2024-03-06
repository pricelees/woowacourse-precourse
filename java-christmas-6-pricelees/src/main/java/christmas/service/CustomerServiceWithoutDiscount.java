package christmas.service;

import christmas.constants.Constants;
import christmas.domain.Customer;
import christmas.util.PriceFormatter;

public class CustomerServiceWithoutDiscount extends CustomerService {
    public CustomerServiceWithoutDiscount(final Customer customer) {
        super(customer);
    }

    @Override
    public String showFreeMenu() {
        return Constants.NONE;
    }

    @Override
    public String showDiscountDescription() {
        return Constants.NONE;
    }

    @Override
    public String showEventBadge() {
        return Constants.NONE;
    }

    @Override
    public String showTotalBenefitsAmount() {
        return PriceFormatter.format(Constants.ZERO);
    }

    @Override
    public String showExpectedAmountToPay() {
        return PriceFormatter.format(customer.orderAmount());
    }
}
