package christmas.util;

import christmas.constants.discount.DiscountErrorMessage;
import christmas.constants.time.EventTime;
import christmas.discount.ChristmasDiscount;
import christmas.discount.DiscountStrategy;
import christmas.discount.SpecialDiscount;
import christmas.discount.WeekdayDiscount;
import christmas.discount.WeekendDiscount;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record DiscountProviderByDate(LocalDateTime visitDate) {
    public List<DiscountStrategy> provide() {
        validateVisitDate(visitDate);
        int dayOfMonth = visitDate.getDayOfMonth();
        DayOfWeek dayOfWeek = visitDate.getDayOfWeek();

        return getAvailableDiscounts(dayOfMonth, dayOfWeek);
    }

    private List<DiscountStrategy> getAvailableDiscounts(int dayOfMonth, DayOfWeek dayOfWeek) {
        List<DiscountStrategy> result = new ArrayList<>();
        if (isBeforeChristmas(dayOfMonth)) {
            result.add(new ChristmasDiscount());
        }
        if (isWeekday(dayOfWeek)) {
            result.add(new WeekdayDiscount());
        }
        if (isWeekend(dayOfWeek)) {
            result.add(new WeekendDiscount());
        }
        if (isSpecialDay(dayOfMonth)) {
            result.add(new SpecialDiscount());
        }
        return result;
    }

    private boolean isBeforeChristmas(int dayOfMonth) {
        return dayOfMonth <= EventTime.DAY_OF_MONTH_CHRISTMAS;
    }

    private boolean isWeekday(DayOfWeek dayOfWeek) {
        return EventTime.WEEKDAYS.contains(dayOfWeek);
    }

    private boolean isWeekend(DayOfWeek dayOfWeek) {
        return EventTime.WEEKENDS.contains(dayOfWeek);
    }

    private boolean isSpecialDay(int dayOfMonth) {
        return EventTime.SPECIAL_DISCOUNT_DAYS.contains(dayOfMonth);
    }

    // 할인 정보를 얻어내는데 '일' 정보만 사용하므로, '년','월'에 대해선 예외 검증 필요
    private void validateVisitDate(LocalDateTime visitDate) {
        if (visitDate.getYear() != EventTime.YEAR || visitDate.getMonth() != EventTime.MONTH) {
            throw new IllegalArgumentException(DiscountErrorMessage.YEAR_AND_MONTH_ERROR.getErrorMessage());
        }
    }
}
