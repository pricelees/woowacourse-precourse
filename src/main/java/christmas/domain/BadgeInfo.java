package christmas.domain;

import static christmas.constants.badge.EventBadge.SANTA_BADGE;
import static christmas.constants.badge.EventBadge.STAR_BADGE;
import static christmas.constants.badge.EventBadge.TREE_BADGE;

import christmas.constants.Constants;

public final class BadgeInfo {
    public String getBadgeName(int purchaseAmount) {
        if (purchaseAmount >= SANTA_BADGE.getMinimumAmountToGet()) {
            return SANTA_BADGE.toString();
        }
        if (purchaseAmount >= TREE_BADGE.getMinimumAmountToGet()) {
            return TREE_BADGE.toString();
        }
        if (purchaseAmount >= STAR_BADGE.getMinimumAmountToGet()) {
            return STAR_BADGE.toString();
        }
        return Constants.NONE;
    }
}
