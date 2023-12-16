package oncall.domain;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Objects;
import oncall.constant.Date;

public class WorkingDate {
    private final Month month;
    private final int dayOfMonth;
    private final DayOfWeek dayOfWeek;

    public WorkingDate(Month month, int dayOfMonth, DayOfWeek dayOfWeek) {
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
    }

    public WorkingDate getYesterday() {
        if (dayOfMonth == 1) {
            return this;
        }
        String today = Date.getDayOfWeekKorean(dayOfWeek);
        DayOfWeek dayOfWeekOfYesterday = Date.getDayOfWeekFromIndex((Date.getDayOfWeekIndex(today) - 1 + 7) % 7);
        return new WorkingDate(month, dayOfMonth - 1, dayOfWeekOfYesterday);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkingDate that = (WorkingDate) o;
        return getDayOfMonth() == that.getDayOfMonth() && getMonth() == that.getMonth()
                && getDayOfWeek() == that.getDayOfWeek();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMonth(), getDayOfMonth(), getDayOfWeek());
    }

    @Override
    public String toString() {
        return "WorkingDate{" +
                "month=" + month +
                ", dayOfMonth=" + dayOfMonth +
                ", dayOfWeek=" + dayOfWeek +
                '}';
    }
}
