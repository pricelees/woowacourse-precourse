package christmas.service;

import christmas.constants.Constants;
import christmas.domain.BadgeContainer;
import christmas.domain.BenefitsContainer;
import christmas.domain.Customer;
import christmas.util.PriceFormatter;
import java.util.stream.Collectors;

public class CustomerServiceWithDiscount extends CustomerService {
    private static final String ONE_CHAMPAGNE = "샴페인 1개";
    private final BenefitsContainer benefitsContainer;

    public CustomerServiceWithDiscount(Customer customer) {
        super(customer);
        this.benefitsContainer = BenefitsContainer.of(customer);
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
        return benefitsContainer.discountTypes().stream()
                .map(type -> type.getTypeName()
                        + Constants.COLON_WITH_SPACE
                        + PriceFormatter.format(type.getDiscountAmount(customer)))
                .collect(Collectors.joining(Constants.LINE_SEPARATOR));
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
