package christmas.domain;

import christmas.constants.Constants;
import christmas.discount.ChampagneDiscount;
import christmas.discount.Discount;
import christmas.discount.DiscountStrategy;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountProvider {
    public static int totalBenefitsAmount(final Customer customer) {
        return Discount.loadAllDiscounts().stream()
                .mapToInt(type -> type.getDiscountAmount(customer))
                .sum();
    }

    public static int actualDiscountAmount(final Customer customer) {
        return Discount.loadAllDiscounts().stream()
                .filter(DiscountProvider::isNotChampagneDiscount)
                .mapToInt(type -> type.getDiscountAmount(customer))
                .sum();
    }

    public static String allDiscountsDescription(final Customer customer) {
        return availableDiscounts(customer).stream()
                .map(type -> type.getDescription(customer))
                .collect(Collectors.joining(Constants.LINE_SEPARATOR));
    }

    private static List<DiscountStrategy> availableDiscounts(final Customer customer) {
        return Discount.loadAllDiscounts().stream()
                .filter(type -> canDiscount(type, customer))
                .toList();
    }

    private static boolean canDiscount(final DiscountStrategy discountStrategy, final Customer customer) {
        return discountStrategy.getDiscountAmount(customer) != Constants.ZERO;
    }

    private static boolean isNotChampagneDiscount(final DiscountStrategy discountStrategy) {
        return !(discountStrategy instanceof ChampagneDiscount);
    }
}
