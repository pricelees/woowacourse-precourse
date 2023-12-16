package oncall.domain;

import java.time.DayOfWeek;
import java.time.Month;

public class WorkingDate {
    private final Month month;
    private final int dayOfMonth;
    private final DayOfWeek dayOfWeek;

    public WorkingDate(Month month, int dayOfMonth, DayOfWeek dayOfWeek) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
    }

    public WorkingDate getBeforeDay() {
        return new WorkingDate(month, dayOfMonth - 1, dayOfWeek);
    }

    public Month getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}
