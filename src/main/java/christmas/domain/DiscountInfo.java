package christmas.domain;

import christmas.constants.Constants;
import christmas.discount.ChampagneDiscount;
import christmas.discount.DiscountStrategy;
import christmas.util.DiscountProviderByDate;
import java.time.LocalDateTime;
import java.util.List;

public record DiscountInfo(
        LocalDateTime visitTime,
        SelectedMenu selectedMenu,
        List<DiscountStrategy> discountTypes) {
    private static final int MINIMUM_AMOUNT_TO_GET_CHAMPAGNE = 120_000;
    private static final int MINIMUM_AMOUNT_TO_DISCOUNT = 10_000;

    public static DiscountInfo valueOf(LocalDateTime visitTime, SelectedMenu selectedMenu) {
        DiscountProviderByDate provider = new DiscountProviderByDate(visitTime);
        List<DiscountStrategy> discountTypes = provider.provide();

        if (selectedMenu.getTotalAmountBeforeDiscount() >= MINIMUM_AMOUNT_TO_GET_CHAMPAGNE) {
            discountTypes.add(new ChampagneDiscount());
        }

        return new DiscountInfo(visitTime, selectedMenu, discountTypes);
    }

    public int calculateBenefitsAmount() {
        if (selectedMenu.getTotalAmountBeforeDiscount() < MINIMUM_AMOUNT_TO_DISCOUNT) {
            return Constants.ZERO;
        }
        return discountTypes.stream()
                .mapToInt(discountType -> discountType.getDiscountAmount(visitTime, selectedMenu))
                .sum();
    }

    public int calculateDiscountAmount() {
        if (selectedMenu.getTotalAmountBeforeDiscount() < MINIMUM_AMOUNT_TO_DISCOUNT) {
            return Constants.ZERO;
        }
        return discountTypes.stream()
                .filter(discountType -> discountType.getClass() != ChampagneDiscount.class)
                .mapToInt(discountType -> discountType.getDiscountAmount(visitTime, selectedMenu))
                .sum();
    }

    public int calculateAmountAfterDiscount() {
        return selectedMenu().getTotalAmountBeforeDiscount() + calculateDiscountAmount();
    }

    public boolean hasFreeChampagne() {
        return selectedMenu.getTotalAmountBeforeDiscount() >= MINIMUM_AMOUNT_TO_GET_CHAMPAGNE;
    }
}
