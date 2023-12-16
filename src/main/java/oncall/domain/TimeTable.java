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
                weekdayWorkers = addTimeTable(workingDate, currentWeekdayOrder, weekdayWorkers);
                currentWeekdayOrder++;
            }
            if (Date.isWeekend(dayOfWeek) || Date.isHoliday(workingDate)) {
                holidayWorkers = addTimeTable(workingDate, currentHolidayOrder, holidayWorkers);
                currentHolidayOrder++;
            }
        }
    }

    private List<Worker> addTimeTable(WorkingDate workingDate, int order, List<Worker> workers) {
        int rearrangedOrder = rearrangeOrder(workers, order);
        List<Worker> rearrangedWorkers = changeWorkersIfDuplicate(workingDate, rearrangedOrder, workers);
        table.put(workingDate, rearrangedWorkers.get(rearrangedOrder));

        return rearrangedWorkers;
    }

    private List<Worker> changeWorkersIfDuplicate(WorkingDate workingDate, int currentOrder, List<Worker> workers) {
        List<Worker> result = new ArrayList<>(workers);
        Worker todayWorker = workers.get(currentOrder);
        Worker yesterdayWorker = table.get(workingDate.getYesterday());
        if (todayWorker.equals(yesterdayWorker)) {
            Worker nextWorker = workers.get(rearrangeOrder(workers, currentOrder + 1));
            result.set(currentOrder, nextWorker);
            result.set(rearrangeOrder(workers, currentOrder + 1), todayWorker);
        }

        return result;
    }

    private int rearrangeOrder(List<Worker> workers, int index) {
        return (index + workers.size()) % workers.size();
    }

    public Map<WorkingDate, Worker> getTable() {
        return table;
    }

    public static void main(String[] args) {
    }
}
