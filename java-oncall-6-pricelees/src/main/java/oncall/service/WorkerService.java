package oncall.service;

import java.util.List;
import java.util.Map;
import oncall.constant.Date;
import oncall.domain.TimeTable;
import oncall.domain.Worker;
import oncall.domain.WorkingDate;
import oncall.dto.TimeResponse;
import oncall.dto.TimeTableResponse;
import oncall.dto.WorkerNamesRequests.WorkerNamesRequest;

public class WorkerService {
    private static final String HOLIDAY = "(휴일)";

    public List<Worker> createWorkerList(WorkerNamesRequest workerNamesRequest) {
        return workerNamesRequest.workerNames().stream()
                .map(Worker::fromName)
                .toList();
    }

    public TimeTableResponse showTimeTableOfThisMonth(TimeTable timeTable) {
        return new TimeTableResponse(timeTable.getTable()
                .entrySet()
                .stream()
                .map(this::mapTimeEntry)
                .toList()
        );
    }

    private TimeResponse mapTimeEntry(Map.Entry<WorkingDate, Worker> workerEntry) {
        WorkingDate workingDate = workerEntry.getKey();
        Worker worker = workerEntry.getValue();

        String dayOfWeek = Date.getDayOfWeekKorean(workingDate.getDayOfWeek());
        if (Date.isHoliday(workingDate) && Date.isWeekday(workingDate.getDayOfWeek())) {
            dayOfWeek += HOLIDAY;
        }

        return new TimeResponse(
                Date.getIntegerMonth(workingDate.getMonth()),
                workingDate.getDayOfMonth(),
                dayOfWeek,
                worker.getWorkerName()
        );
    }
}
