package christmas.constants.time;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.List;

public class EventTime {
    public static final List<Integer> SPECIAL_DISCOUNT_DAYS = List.of(3, 10, 17, 24, 25, 31);
    public static final List<DayOfWeek> WEEKDAYS = List.of(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY
    );
    public static final List<DayOfWeek> WEEKENDS = List.of(
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
    );
    public static final Month MONTH = Month.DECEMBER;
    public static final int YEAR = 2023;
    public static final int HOUR = 17;
    public static final int MINUTE = 0;
    public static final int SECOND = 0;
    public static final int DAY_OF_MONTH_EVENTS_START = 1;
    public static final int DAY_OF_MONTH_CHRISTMAS = 25;
}
