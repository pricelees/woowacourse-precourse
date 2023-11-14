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
