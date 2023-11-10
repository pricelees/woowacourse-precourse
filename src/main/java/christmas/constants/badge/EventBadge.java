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

    public int getMinimumAmountToGet() {
        return minimumAmountToGet;
    }

    @Override
    public String toString() {
        return koreanName;
    }
}
