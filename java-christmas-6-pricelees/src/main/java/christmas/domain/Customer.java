package christmas.domain;

import christmas.constants.menu.Menu;
import java.time.LocalDateTime;
import java.util.Map;

public record Customer(DateToVisit dateToVisit, SelectedMenu selectedMenu) {
    private static final int MINIMUM_AMOUNT_TO_GET_CHAMPAGNE = 120_000;
    private static final int MINIMUM_AMOUNT_TO_PARTICIPATE_EVENT = 10_000;

    public Customer(final LocalDateTime date, final Map<Menu, Integer> menuToOrder) {
        this(new DateToVisit(date), new SelectedMenu(menuToOrder));
    }

    public int orderAmount() {
        return selectedMenu.getTotalAmountBeforeDiscount();
    }

    public String orderedMenu() {
        return selectedMenu.toString();
    }

    public int countDessert() {
        return selectedMenu.getDessertCounts();
    }

    public int countMainMenu() {
        return selectedMenu.getMainMenuCounts();
    }

    public boolean canReceiveFreeChampagne() {
        return orderAmount() >= MINIMUM_AMOUNT_TO_GET_CHAMPAGNE;
    }

    public boolean cannotParticipateEvent() {
        return orderAmount() < MINIMUM_AMOUNT_TO_PARTICIPATE_EVENT;
    }
}
