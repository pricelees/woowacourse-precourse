package christmas.domain;

import christmas.constants.domain.DomainErrorMessage;
import christmas.constants.time.EventTime;
import java.time.LocalDateTime;
import java.time.Month;

public record DateToVisit(LocalDateTime date) {
    public DateToVisit {
        validateDate(date);
    }

    public int dayOfMonth() {
        return date.getDayOfMonth();
    }

    public boolean isBeforeChristmas() {
        return date.getDayOfMonth() <= EventTime.DAY_OF_MONTH_CHRISTMAS;
    }

    public boolean isWeekday() {
        return EventTime.WEEKDAYS.contains(date.getDayOfWeek());
    }

    public boolean isWeekend() {
        return EventTime.WEEKENDS.contains(date.getDayOfWeek());
    }

    public boolean isSpecialDay() {
        return EventTime.SPECIAL_DISCOUNT_DAYS.contains(date.getDayOfMonth());
    }

    // 고객이 방문할 년, 월에 대한 검증
    private void validateDate(final LocalDateTime dateToVisit) {
        validateYear(dateToVisit.getYear());
        validateMonth(dateToVisit.getMonth());
    }

    private void validateYear(final int year) {
        if (year != EventTime.YEAR) {
            throw new IllegalArgumentException(DomainErrorMessage.INVALID_YEAR_ERROR.getErrorMessage());
        }
    }

    private void validateMonth(final Month month) {
        if (month != EventTime.MONTH) {
            throw new IllegalArgumentException(DomainErrorMessage.INVALID_MONTH_ERROR.getErrorMessage());
        }
    }
}
