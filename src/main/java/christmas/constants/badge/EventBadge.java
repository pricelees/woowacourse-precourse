package christmas.constants.badge;

public enum EventBadge {
    SANTA_BADGE("산타", 20_000),
    TREE_BADGE("트리", 10_000),
    STAR_BADGE("별", 5_000);

    private final String koreanName;
    private final int minimumAmountToGet;

    EventBadge(String koreanName, int minimumAmountToGet) {
        this.koreanName = koreanName;
        this.minimumAmountToGet = minimumAmountToGet;
    }

    public static EventBadge getFromPurchaseAmount(int purchaseAmount) {
        if (purchaseAmount >= SANTA_BADGE.minimumAmountToGet) {
            return SANTA_BADGE;
        }
        if (purchaseAmount >= TREE_BADGE.minimumAmountToGet) {
            return TREE_BADGE;
        }
        if (purchaseAmount >= STAR_BADGE.minimumAmountToGet) {
            return STAR_BADGE;
        }
        return null;
    }

    @Override
    public String toString() {
        return koreanName;
    }
}
