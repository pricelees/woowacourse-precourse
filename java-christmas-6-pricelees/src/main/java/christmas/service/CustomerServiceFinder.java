package christmas.service;

import christmas.constants.menu.Menu;
import christmas.domain.Customer;
import java.time.LocalDateTime;
import java.util.Map;

public class CustomerServiceFinder {
    public static CustomerService find(final LocalDateTime dateToVisit, final Map<Menu, Integer> menuToOrder) {
        Customer customer = new Customer(dateToVisit, menuToOrder);
        if (customer.cannotParticipateEvent()) {
            return new CustomerServiceWithoutDiscount(customer);
        }
        return new CustomerServiceWithDiscount(customer);
    }
}
