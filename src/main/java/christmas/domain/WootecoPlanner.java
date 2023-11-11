package christmas.domain;

import christmas.constants.Constants;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public record WootecoPlanner(
        LocalDateTime dateToVisit,
        SelectedMenu selectedMenu,
        DiscountInfo discountInfo,
        BadgeInfo badgeInfo
) {
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("###,###원");
    private static final String COLON_WITH_SPACE = ": ";
    private static final String ONE_CHAMPAGNE = "샴페인 1개";
    private static final int MINIMUM_AMOUNT_FOR_EVENT = 10_000;

    public static WootecoPlanner valueOf(LocalDateTime date, SelectedMenu selectedMenu) {
        return new WootecoPlanner(date, selectedMenu, DiscountInfo.valueOf(date, selectedMenu), new BadgeInfo());
    }

    public String showOrderedMenu() {
        return selectedMenu.toString();
    }

    public String showAmountBeforeDiscount() {
        return PRICE_FORMATTER.format(selectedMenu.getTotalAmountBeforeDiscount());
    }

    public String showFreeMenu() {
        if (discountInfo.hasFreeChampagne()) {
            return ONE_CHAMPAGNE;
        }

        return Constants.NONE;
    }

    public String showDiscountDescription() {
        if (cantParticipateEvent()) {
            return Constants.NONE;
        }

        return discountInfo.discountTypes().stream()
                .map(type -> type.getTypeName()
                        + COLON_WITH_SPACE
                        + PRICE_FORMATTER.format(type.getDiscountAmount(dateToVisit, selectedMenu)))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String showTotalBenefitsAmount() {
        return PRICE_FORMATTER.format(discountInfo.calculateBenefitsAmount());
    }

    public String showExpectedAmountToPay() {
        return PRICE_FORMATTER.format(discountInfo.calculateAmountAfterDiscount());
    }

    public String showEventBadge() {
        if (cantParticipateEvent()) {
            return Constants.NONE;
        }
        return badgeInfo.getBadgeName(discountInfo.calculateAmountAfterDiscount());
    }

    private boolean cantParticipateEvent() {
        return selectedMenu.getTotalAmountBeforeDiscount() < MINIMUM_AMOUNT_FOR_EVENT;
    }
}
