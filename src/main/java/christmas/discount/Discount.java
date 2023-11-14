package christmas.discount;

import java.util.Arrays;
import java.util.List;

public enum Discount {
    CHRISTMAS_D_DAY_DISCOUNT(ChristmasDiscount.getInstance()),
    WEEKDAY_DISCOUNT(WeekdayDiscount.getInstance()),
    WEEKEND_DISCOUNT(WeekendDiscount.getInstance()),
    SPECIAL_DISCOUNT(SpecialDiscount.getInstance()),
    CHAMPAGNE_DISCOUNT(ChampagneDiscount.getInstance());

    private final DiscountStrategy discountStrategy;

    Discount(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public static List<DiscountStrategy> loadAllDiscounts() {
        return Arrays.stream(Discount.values())
                .map(Discount::getDiscountStrategy)
                .toList();
    }

    private DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }
}
