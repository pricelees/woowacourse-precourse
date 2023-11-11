package christmas.domain;

import christmas.constants.Constants;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

public record WootecoPlanner(
        Customer customer,
        DiscountInfo discountInfo,
        BadgeInfo badgeInfo
) {
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("###,###원");

    private static final String ONE_CHAMPAGNE = "샴페인 1개";

    public static WootecoPlanner valueOf(Customer customer) {
        DiscountInfo discounts = DiscountInfo.valueOf(customer);
        BadgeInfo badge = new BadgeInfo(discounts.calculateAmountAfterDiscount());

        return new WootecoPlanner(customer, discounts, badge);
    }

    public String showOrderedMenu() {
        return customer.orderedMenu();
    }

    public String showAmountBeforeDiscount() {
        return PRICE_FORMATTER.format(customer.orderAmount());
    }

    public String showFreeMenu() {
        if (customer.canReceiveFreeChampagne()) {
            return ONE_CHAMPAGNE;
        }

        return Constants.NONE;
    }

    public String showDiscountDescription() {
        if (customer.cannotParticipateEvent()) {
            return Constants.NONE;
        }

        return discountInfo.discountTypes().stream()
                .map(type -> type.getTypeName()
                        + Constants.COLON_WITH_SPACE
                        + PRICE_FORMATTER.format(type.getDiscountAmount(customer)))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String showTotalBenefitsAmount() {
        return PRICE_FORMATTER.format(discountInfo.calculateBenefitsAmount());
    }

    public String showExpectedAmountToPay() {
        return PRICE_FORMATTER.format(discountInfo.calculateAmountAfterDiscount());
    }

    public String showEventBadge() {
        if (customer.cannotParticipateEvent()) {
            return Constants.NONE;
        }
        return badgeInfo.getBadgeName();
    }
}
