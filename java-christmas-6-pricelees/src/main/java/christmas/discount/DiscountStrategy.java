package christmas.discount;

import christmas.domain.Customer;

public interface DiscountStrategy {
    int getDiscountAmount(Customer customer);

    String getDescription(Customer customer);
}
