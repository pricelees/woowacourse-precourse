package christmas.domain;

import christmas.constants.Constants;
import christmas.discount.AvailableDiscountsProvider;
import christmas.discount.DiscountStrategy;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record DiscountInfo(
        LocalDateTime visitTime,
        SelectedMenu selectedMenu,
        List<DiscountStrategy> discountTypes) {
    private static final String DISCOUNT_DESCRIPTION_FORMAT = "%s: -%s";
    private static final String DISCOUNT_AMOUNT_FORMAT = "-%s";
    private static final String ONE_CHAMPAGNE = "샴페인 1개";
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("###,###원");
    private static final int MINIMUM_AMOUNT_TO_GET_CHAMPAGNE = 120_000;
    private static final int MINIMUM_AMOUNT_TO_DISCOUNT = 10_000;
    private static final int ONE_CHAMPAGNE_PRICE = 25_000;

    public static DiscountInfo valueOf(LocalDateTime visitTime, SelectedMenu selectedMenu) {
        AvailableDiscountsProvider provider = new AvailableDiscountsProvider();
        List<DiscountStrategy> discountTypes = provider.provide(visitTime, selectedMenu);

        return new DiscountInfo(visitTime, selectedMenu, discountTypes);
    }

    private int calculateDiscountAmount() {
        if (selectedMenu.getTotalAmountBeforeDiscount() < MINIMUM_AMOUNT_TO_DISCOUNT) {
            return Constants.ZERO;
        }
        return discountTypes.stream()
                .mapToInt(discountType -> discountType.getDiscountAmount(visitTime, selectedMenu))
                .sum();
    }

    private int calculateAmountBeforeDiscount() {
        return selectedMenu.getTotalAmountBeforeDiscount();
    }

    public int calculateAmountAfterDiscount() {
        int amountAfterDiscount = calculateAmountBeforeDiscount() - calculateAmountBeforeDiscount();
        if (hasFreeChampagne()) {
            return amountAfterDiscount + ONE_CHAMPAGNE_PRICE;
        }
        return amountAfterDiscount;
    }

    public String discountDescription() {
        if (selectedMenu.getTotalAmountBeforeDiscount() < MINIMUM_AMOUNT_TO_DISCOUNT) {
            return Constants.NONE;
        }

        return discountTypes.stream()
                .filter(type -> type.getDiscountAmount(visitTime, selectedMenu) > Constants.ZERO)
                .map(type -> String.format(
                                DISCOUNT_DESCRIPTION_FORMAT,
                                type.getTypeName(),
                                PRICE_FORMATTER.format(type.getDiscountAmount(visitTime, selectedMenu))
                        )
                )
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String freeChampagneInfo() {
        if (hasFreeChampagne()) {
            return ONE_CHAMPAGNE;
        }
        return Constants.NONE;
    }

    private boolean hasFreeChampagne() {
        return selectedMenu.getTotalAmountBeforeDiscount() >= MINIMUM_AMOUNT_TO_GET_CHAMPAGNE;
    }

    public String amountBeforeDiscount() {
        return PRICE_FORMATTER.format(calculateAmountBeforeDiscount());
    }

    public String amountAfterDiscount() {
        int calculatedDiscountAmount = calculateDiscountAmount();
        String formattedDiscountAmount = PRICE_FORMATTER.format(calculatedDiscountAmount);

        if (calculatedDiscountAmount == Constants.ZERO) {
            return formattedDiscountAmount;
        }
        return String.format(DISCOUNT_AMOUNT_FORMAT, formattedDiscountAmount);
    }

    public String expectedAmount() {
        return PRICE_FORMATTER.format(calculateAmountAfterDiscount());
    }
}
