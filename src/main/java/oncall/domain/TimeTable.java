package oncall.domain;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import oncall.constant.Date;

public class TimeTable {
    private final Map<WorkingDate, Worker> table;
    private final WorkingCalendar workingCalendar;

    public TimeTable(Map<WorkingDate, Worker> table, WorkingCalendar workingCalendar) {
        this.table = table;
        this.workingCalendar = workingCalendar;
    }

    public static TimeTable from(WorkingCalendar workingCalendar) {
        return new TimeTable(
                new LinkedHashMap<>(),
                workingCalendar
        );
    }

    public void updateTable(Month month, List<Worker> weekdayWorkers, List<Worker> holidayWorkers) {
        int maxDayOfMonth = Date.getMaxDayOfMonth(month);
        int currentWeekdayOrder = 0;
        int currentHolidayOrder = 0;

        for (int dayOfMonth = 1; dayOfMonth <= maxDayOfMonth; dayOfMonth++) {
            DayOfWeek dayOfWeek = workingCalendar.getDayOfWeek(dayOfMonth);
            WorkingDate workingDate = new WorkingDate(month, dayOfMonth, dayOfWeek);
            if (Date.isWeekday(dayOfWeek)) {
                List<Worker> workers = changeWorkersIfDuplicate(workingDate, currentWeekdayOrder, weekdayWorkers);
                table.put(workingDate, workers.get(currentWeekdayOrder));
                currentWeekdayOrder++;
            }
            if (Date.isWeekend(dayOfWeek) || Date.isHoliday(workingDate)) {
                List<Worker> workers = changeWorkersIfDuplicate(workingDate, currentHolidayOrder, holidayWorkers);
                table.put(workingDate, workers.get(currentHolidayOrder));
                currentHolidayOrder++;
            }
        }
    }

    private List<Worker> changeWorkersIfDuplicate(WorkingDate workingDate, int currentOrder, List<Worker> workers) {
        List<Worker> result = new ArrayList<>(workers);
        Worker currentWorker = workers.get(currentOrder);
        Worker beforeWorker = table.get(workingDate.getBeforeDay());
        if (currentWorker.equals(beforeWorker)) {
            Worker nextWorker = workers.get(currentOrder + 1);
            result.set(currentOrder, nextWorker);
            result.set(currentOrder + 1, currentWorker);
        }

        return result;
    }

    public Map<WorkingDate, Worker> getTable() {
        return table;
    }
}
