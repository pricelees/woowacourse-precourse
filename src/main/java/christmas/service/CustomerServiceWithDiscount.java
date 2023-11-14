package christmas.service;

import christmas.constants.Constants;
import christmas.domain.BadgeContainer;
import christmas.domain.BenefitsContainer;
import christmas.domain.Customer;
import christmas.util.PriceFormatter;

public class CustomerServiceWithDiscount extends CustomerService {
    private static final String ONE_CHAMPAGNE = "샴페인 1개";
    private final BenefitsContainer benefitsContainer;

    public CustomerServiceWithDiscount(Customer customer) {
        super(customer);
        this.benefitsContainer = new BenefitsContainer(customer);
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
        return benefitsContainer.getDiscountDescription();
    }

    @Override
    public String showTotalBenefitsAmount() {
        return PriceFormatter.format(benefitsContainer.calculateBenefitsAmount());
    }

    @Override
    public String showExpectedAmountToPay() {
        return PriceFormatter.format(benefitsContainer.calculateAmountAfterDiscount());
    }

    @Override
    public String showEventBadge() {
        BadgeContainer badgeContainer = new BadgeContainer(benefitsContainer.calculateAmountAfterDiscount());
        return badgeContainer.getBadgeName();
    }
}
