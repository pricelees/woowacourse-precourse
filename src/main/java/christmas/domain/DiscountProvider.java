package christmas.domain;

import christmas.constants.Constants;
import christmas.discount.ChampagneDiscount;
import christmas.discount.ChristmasDiscount;
import christmas.discount.DiscountStrategy;
import christmas.discount.SpecialDiscount;
import christmas.discount.WeekdayDiscount;
import christmas.discount.WeekendDiscount;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountProvider {
    private static final List<DiscountStrategy> ALL_DISCOUNT_TYPES = List.of(
            ChristmasDiscount.getInstance(),
            WeekdayDiscount.getInstance(),
            WeekendDiscount.getInstance(),
            SpecialDiscount.getInstance(),
            ChampagneDiscount.getInstance()
    );

    public static int totalBenefitsAmount(Customer customer) {
        return ALL_DISCOUNT_TYPES.stream()
                .mapToInt(type -> type.getDiscountAmount(customer))
                .sum();
    }

    public static int actualDiscountAmount(Customer customer) {
        return ALL_DISCOUNT_TYPES.stream()
                .filter(DiscountProvider::isNotChampagneDiscount)
                .mapToInt(type -> type.getDiscountAmount(customer))
                .sum();
    }

    public static String allDiscountsDescription(Customer customer) {
        return availableDiscounts(customer).stream()
                .map(type -> type.getDescription(customer))
                .collect(Collectors.joining(Constants.LINE_SEPARATOR));
    }

    private static List<DiscountStrategy> availableDiscounts(Customer customer) {
        return ALL_DISCOUNT_TYPES.stream()
                .filter(type -> canDiscount(type, customer))
                .toList();
    }

    private static boolean canDiscount(DiscountStrategy discountStrategy, Customer customer) {
        return discountStrategy.getDiscountAmount(customer) != Constants.ZERO;
    }

    private static boolean isNotChampagneDiscount(DiscountStrategy discountStrategy) {
        return !(discountStrategy instanceof ChampagneDiscount);
    }
}
