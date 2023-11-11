package christmas.domain;

import java.time.LocalDateTime;

public record Customer(LocalDateTime dateToVisit, SelectedMenu selectedMenu) {
    private static final int MINIMUM_AMOUNT_TO_GET_CHAMPAGNE = 120_000;
    private static final int MINIMUM_AMOUNT_TO_PARTICIPATE_EVENT = 10_000;

    public int dayOfMonthToVisit() {
        return dateToVisit.getDayOfMonth();
    }

    public int orderAmount() {
        return selectedMenu().getTotalAmountBeforeDiscount();
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
