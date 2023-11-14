package christmas.domain;

import christmas.constants.Constants;
import christmas.discount.ChampagneDiscount;
import christmas.discount.DiscountStrategy;
import christmas.util.DiscountProviderByDate;
import christmas.util.PriceFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record BenefitsContainer(Customer customer) {
    public int calculateBenefitsAmount() {
        return discountTypeStream()
                .mapToInt(discountType -> discountType.getDiscountAmount(customer))
                .sum();
    }

    public int calculateDiscountAmount() {
        return discountTypeStream()
                .filter(discountType -> discountType.getClass() != ChampagneDiscount.class)
                .mapToInt(discountType -> discountType.getDiscountAmount(customer))
                .sum();
    }

    public int calculateAmountAfterDiscount() {
        return customer.orderAmount() + calculateDiscountAmount();
    }

    public String getDiscountDescription() {
        return discountTypeStream()
                .map(type -> type.getTypeName()
                        + Constants.COLON_WITH_SPACE
                        + PriceFormatter.format(type.getDiscountAmount(customer)))
                .collect(Collectors.joining(Constants.LINE_SEPARATOR));
    }

    private Stream<DiscountStrategy> discountTypeStream() {
        return receiveDiscountTypes().stream();
    }

    private List<DiscountStrategy> receiveDiscountTypes() {
        DiscountProviderByDate provider = new DiscountProviderByDate(customer.dateToVisit());
        List<DiscountStrategy> result = provider.provide();

        return addChampagneDiscountIfPossible(result);
    }

    private List<DiscountStrategy> addChampagneDiscountIfPossible(final List<DiscountStrategy> discountTypes) {
        List<DiscountStrategy> result = new ArrayList<>(discountTypes);
        if (customer.canReceiveFreeChampagne()) {
            result.add(new ChampagneDiscount());
        }

        return Collections.unmodifiableList(result);
    }
}
