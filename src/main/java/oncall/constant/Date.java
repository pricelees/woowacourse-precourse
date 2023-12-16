package oncall.constant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import oncall.domain.WorkingDate;

public final class Date {
    private Date() {
    }

    public static final List<LocalDate> HOLIDAYS = List.of(
            LocalDate.of(2023, 1, 1),
            LocalDate.of(2023, 3, 1),
            LocalDate.of(2023, 5, 5),
            LocalDate.of(2023, 6, 6),
            LocalDate.of(2023, 8, 15),
            LocalDate.of(2023, 10, 3),
            LocalDate.of(2023, 10, 9),
            LocalDate.of(2023, 12, 25)
    );

    public static final List<DayOfWeek> WEEKEND = List.of(
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
    );

    public static final List<DayOfWeek> WEEKDAY = List.of(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
    );

    public static boolean isHoliday(WorkingDate workingDate) {
        return HOLIDAYS.contains(LocalDate.of(
                2023, workingDate.getMonth().getValue(), workingDate.getDayOfMonth())
        );
    }

    public static boolean isWeekday(DayOfWeek dayOfWeek) {
        return WEEKDAY.contains(dayOfWeek);
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        return WEEKEND.contains(dayOfWeek);
    }

    public static int getMaxDayOfMonth(Month month) {
        if (month == Month.FEBRUARY) {
            return month.minLength();
        }
        return month.maxLength();
    }

    public static int getDayOfWeekIndex(String dayOfWeek) {
        return Arrays.asList(DayOfWeek.values()).indexOf(getDayOFWeekFromName(dayOfWeek));
    }

    private static DayOfWeek getDayOFWeekFromName(String name) {
        return Arrays.stream(DayOfWeek.values())
                .filter(dayOfWeek -> name.equals(getDayOfWeekKorean(dayOfWeek)))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static DayOfWeek getDayOfWeekFromIndex(int index) {
        return DayOfWeek.values()[(index % DayOfWeek.values().length)];
    }

    public static int getIntegerMonth(Month month) {
        return month.getValue();
    }

    public static String getDayOfWeekKorean(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
    }
}
