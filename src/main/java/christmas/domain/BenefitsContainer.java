package christmas.domain;

import christmas.discount.ChampagneDiscount;
import christmas.discount.DiscountStrategy;
import christmas.util.DiscountProviderByDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record BenefitsContainer(
        Customer customer,
        List<DiscountStrategy> discountTypes) {
    public static BenefitsContainer of(Customer customer) {
        DiscountProviderByDate provider = new DiscountProviderByDate(customer.dateToVisit());
        List<DiscountStrategy> discountTypes = provider.provide();

        if (customer.canReceiveFreeChampagne()) {
            List<DiscountStrategy> discountsWithChampagne = new ArrayList<>(discountTypes);
            discountsWithChampagne.add(new ChampagneDiscount());
            return new BenefitsContainer(customer, Collections.unmodifiableList(discountsWithChampagne));
        }

        return new BenefitsContainer(customer, discountTypes);
    }

    public int calculateBenefitsAmount() {
        return discountTypes.stream()
                .mapToInt(discountType -> discountType.getDiscountAmount(customer))
                .sum();
    }

    public int calculateDiscountAmount() {
        return discountTypes.stream()
                .filter(discountType -> discountType.getClass() != ChampagneDiscount.class)
                .mapToInt(discountType -> discountType.getDiscountAmount(customer))
                .sum();
    }

    public int calculateAmountAfterDiscount() {
        return customer.orderAmount() + calculateDiscountAmount();
    }
}
