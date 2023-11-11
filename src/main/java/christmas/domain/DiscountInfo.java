package christmas.domain;

import christmas.constants.Constants;
import christmas.discount.AvailableDiscountsProvider;
import christmas.discount.DiscountStrategy;
import java.time.LocalDateTime;
import java.util.List;

public record DiscountInfo(
        LocalDateTime visitTime,
        SelectedMenu selectedMenu,
        List<DiscountStrategy> discountTypes) {
    private static final int MINIMUM_AMOUNT_TO_GET_CHAMPAGNE = 120_000;
    private static final int MINIMUM_AMOUNT_TO_DISCOUNT = 10_000;
    private static final int ONE_CHAMPAGNE_PRICE = 25_000;

    public static DiscountInfo valueOf(LocalDateTime visitTime, SelectedMenu selectedMenu) {
        AvailableDiscountsProvider provider = new AvailableDiscountsProvider();
        List<DiscountStrategy> discountTypes = provider.provide(visitTime, selectedMenu);

        return new DiscountInfo(visitTime, selectedMenu, discountTypes);
    }

    public int calculateDiscountAmount() {
        if (selectedMenu.getTotalAmountBeforeDiscount() < MINIMUM_AMOUNT_TO_DISCOUNT) {
            return Constants.ZERO;
        }
        return discountTypes.stream()
                .mapToInt(discountType -> discountType.getDiscountAmount(visitTime, selectedMenu))
                .sum();
    }

    public int calculateAmountBeforeDiscount() {
        return selectedMenu.getTotalAmountBeforeDiscount();
    }

    public int calculateAmountAfterDiscount() {
        int amountAfterDiscount = calculateAmountBeforeDiscount() - calculateDiscountAmount();
        if (hasFreeChampagne()) {
            return amountAfterDiscount + ONE_CHAMPAGNE_PRICE;
        }
        return amountAfterDiscount;
    }

    public boolean hasFreeChampagne() {
        return selectedMenu.getTotalAmountBeforeDiscount() >= MINIMUM_AMOUNT_TO_GET_CHAMPAGNE;
    }
}
