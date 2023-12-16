package oncall.dto;

import java.util.List;

public record WorkerNamesRequests(WorkerNamesRequest weekdayWorkers, WorkerNamesRequest holidayWorkers) {
    public record WorkerNamesRequest(List<String> workerNames) {
    }
}
