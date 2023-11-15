package christmas.service;

import christmas.domain.Customer;
import christmas.domain.DateToVisit;
import christmas.util.PriceFormatter;

public abstract class CustomerService {
    protected final Customer customer;

    public CustomerService(final Customer customer) {
        this.customer = customer;
    }

    public String showOrderedMenu() {
        return customer.orderedMenu();
    }

    public String showAmountBeforeDiscount() {
        return PriceFormatter.format(customer.orderAmount());
    }

    public int getDayOfMonth() {
        DateToVisit dateToVisit = customer.dateToVisit();
        return dateToVisit.dayOfMonth();
    }

    public abstract String showFreeMenu();

    public abstract String showDiscountDescription();

    public abstract String showTotalBenefitsAmount();

    public abstract String showExpectedAmountToPay();

    public abstract String showEventBadge();
}
