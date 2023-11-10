package christmas.domain;

import christmas.constants.Constants;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public record WootecoPlanner(
        LocalDateTime date,
        SelectedMenu selectedMenu,
        DiscountInfo discountInfo,
        BadgeInfo badgeInfo
) {
    private static final String DISCOUNT_DESCRIPTION_FORMAT = "%s: -%s";
    private static final String DISCOUNT_AMOUNT_FORMAT = "-%s";
    private static final String ONE_CHAMPAGNE = "샴페인 1개";
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("###,###원");
    private static final int MINIMUM_AMOUNT_FOR_EVENT = 10_000;

    public static WootecoPlanner valueOf(LocalDateTime date, SelectedMenu selectedMenu) {
        return new WootecoPlanner(date, selectedMenu, DiscountInfo.valueOf(date, selectedMenu), new BadgeInfo());
    }

    public String showOrderedMenu() {
        return selectedMenu.toString();
    }

    public String showAmountBeforeDiscount() {
        return PRICE_FORMATTER.format(discountInfo.calculateAmountBeforeDiscount());
    }

    public String showFreeMenu() {
        if (discountInfo.hasFreeChampagne()) {
            return ONE_CHAMPAGNE;
        }
        return Constants.NONE;
    }

    public String showDiscountDescription() {
        if (selectedMenu.getTotalAmountBeforeDiscount() < MINIMUM_AMOUNT_FOR_EVENT) {
            return Constants.NONE;
        }

        return discountInfo.discountTypes().stream()
                .filter(type -> type.getDiscountAmount(date, selectedMenu) > Constants.ZERO)
                .map(type -> String.format(
                                DISCOUNT_DESCRIPTION_FORMAT,
                                type.getTypeName(),
                                PRICE_FORMATTER.format(type.getDiscountAmount(date, selectedMenu))
                        )
                )
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String showTotalDiscountedAmount() {
        int calculatedDiscountAmount = discountInfo.calculateDiscountAmount();
        String formattedDiscountAmount = PRICE_FORMATTER.format(calculatedDiscountAmount);

        if (calculatedDiscountAmount == Constants.ZERO) {
            return formattedDiscountAmount;
        }

        return String.format(DISCOUNT_AMOUNT_FORMAT, formattedDiscountAmount);
    }

    public String showExpectedAmount() {
        return PRICE_FORMATTER.format(discountInfo.calculateAmountAfterDiscount());
    }

    public String showEventBadge() {
        if (selectedMenu.getTotalAmountBeforeDiscount() < MINIMUM_AMOUNT_FOR_EVENT) {
            return Constants.NONE;
        }
        return badgeInfo.getBadgeName(discountInfo.calculateAmountAfterDiscount());
    }
}
