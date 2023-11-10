package christmas.domain;

import christmas.constants.Constants;
import java.time.LocalDateTime;

public record WootecoPlanner(
        LocalDateTime date,
        SelectedMenu selectedMenu,
        DiscountInfo discountInfo,
        BadgeInfo badgeInfo
) {
    private static final int MINIMUM_AMOUNT_TO_GET_BADGE = 10_000;

    public static WootecoPlanner valueOf(LocalDateTime date, SelectedMenu selectedMenu) {
        return new WootecoPlanner(date, selectedMenu, DiscountInfo.valueOf(date, selectedMenu), new BadgeInfo());
    }

    public String showOrderedMenu() {
        return selectedMenu.toString();
    }

    public String showAmountBeforeDiscount() {
        return discountInfo.amountBeforeDiscount();
    }

    public String showFreeMenu() {
        return discountInfo.freeChampagneInfo();
    }

    public String showDiscountDescription() {
        return discountInfo.discountDescription();
    }

    public String showTotalDiscountedAmount() {
        return discountInfo.amountAfterDiscount();
    }

    public String showExpectedAmount() {
        return discountInfo.expectedAmount();
    }

    public String showEventBadge() {
        if (selectedMenu.getTotalAmountBeforeDiscount() < MINIMUM_AMOUNT_TO_GET_BADGE) {
            return Constants.NONE;
        }
        return badgeInfo.getBadgeName(discountInfo.calculateAmountAfterDiscount());
    }
}
