package christmas.domain;

import static christmas.constants.badge.EventBadge.SANTA_BADGE;
import static christmas.constants.badge.EventBadge.STAR_BADGE;
import static christmas.constants.badge.EventBadge.TREE_BADGE;

import christmas.constants.Constants;

public record BadgeInfo(int amountAfterDiscount) {
    public String getBadgeName() {
        if (amountAfterDiscount >= SANTA_BADGE.getMinimumAmountToGet()) {
            return SANTA_BADGE.toString();
        }
        if (amountAfterDiscount >= TREE_BADGE.getMinimumAmountToGet()) {
            return TREE_BADGE.toString();
        }
        if (amountAfterDiscount >= STAR_BADGE.getMinimumAmountToGet()) {
            return STAR_BADGE.toString();
        }
        return Constants.NONE;
    }
}
