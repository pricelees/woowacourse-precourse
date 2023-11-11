package christmas.domain;

import christmas.constants.domain.DomainErrorMessage;
import christmas.constants.time.EventTime;
import java.time.LocalDateTime;
import java.time.Month;

public record Customer(LocalDateTime dateToVisit, SelectedMenu selectedMenu) {
    private static final int MINIMUM_AMOUNT_TO_GET_CHAMPAGNE = 120_000;
    private static final int MINIMUM_AMOUNT_TO_PARTICIPATE_EVENT = 10_000;

    public Customer {
        validateDate(dateToVisit);
    }

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

    // LocalDateTime을 이용하기에, "일" 정보에 대해 예외를 검증할 필요는 없음.
    private void validateDate(LocalDateTime dateToVisit) {
        validateYear(dateToVisit.getYear());
        validateMonth(dateToVisit.getMonth());
    }

    private void validateYear(int year) {
        if (year != EventTime.YEAR) {
            throw new IllegalArgumentException(DomainErrorMessage.INVALID_YEAR_ERROR.getErrorMessage());
        }
    }

    private void validateMonth(Month month) {
        if (month != EventTime.MONTH) {
            throw new IllegalArgumentException(DomainErrorMessage.INVALID_MONTH_ERROR.getErrorMessage());
        }
    }
}
