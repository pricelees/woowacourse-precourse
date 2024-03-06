package oncall.domain;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;
import oncall.constant.Date;

public class WorkingCalendar {
    private final Map<Integer, DayOfWeek> calendarOfThisMonth;

    public WorkingCalendar(int month, String dayOfWeek) {
        calendarOfThisMonth = initCalendar(month, dayOfWeek);
    }

    private Map<Integer, DayOfWeek> initCalendar(int month, String dayOfWeek) {
        Map<Integer, DayOfWeek> result = new LinkedHashMap<>();
        int startDayOfWeekIndex = Date.getDayOfWeekIndex(dayOfWeek);
        result.put(1, Date.getDayOfWeekFromIndex(startDayOfWeekIndex));

        int maxDayOfMonth = Date.getMaxDayOfMonth(Month.of(month));
        for (int dayOfMonth = 1; dayOfMonth < maxDayOfMonth; dayOfMonth++) {
            result.put(dayOfMonth + 1, Date.getDayOfWeekFromIndex(startDayOfWeekIndex + dayOfMonth));
        }

        return result;
    }

    public Map<Integer, DayOfWeek> getCalendarOfThisMonth() {
        return calendarOfThisMonth;
    }

    public DayOfWeek getDayOfWeek(int dayOfMonth) {
        return calendarOfThisMonth.get(dayOfMonth);
    }
}
