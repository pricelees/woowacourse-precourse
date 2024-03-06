package christmas.service;

import christmas.constants.Constants;
import christmas.domain.BadgeProvider;
import christmas.domain.Customer;
import christmas.domain.DiscountProvider;
import christmas.util.PriceFormatter;

public class CustomerServiceWithDiscount extends CustomerService {
    private static final String ONE_CHAMPAGNE = "샴페인 1개";

    public CustomerServiceWithDiscount(Customer customer) {
        super(customer);
    }

    @Override
    public String showFreeMenu() {
        if (customer.canReceiveFreeChampagne()) {
            return ONE_CHAMPAGNE;
        }
        return Constants.NONE;
    }

    @Override
    public String showDiscountDescription() {
        return DiscountProvider.allDiscountsDescription(customer);
    }

    @Override
    public String showTotalBenefitsAmount() {
        return PriceFormatter.format(DiscountProvider.totalBenefitsAmount(customer));
    }

    @Override
    public String showExpectedAmountToPay() {
        return PriceFormatter.format(calculateAmountAfterDiscount());
    }

    @Override
    public String showEventBadge() {
        return BadgeProvider.getBadgeName(calculateAmountAfterDiscount());
    }

    private int calculateAmountAfterDiscount() {
        return customer.orderAmount() + DiscountProvider.actualDiscountAmount(customer);
    }
}
