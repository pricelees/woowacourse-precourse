package oncall.controller;

import java.time.Month;
import java.util.List;
import oncall.domain.TimeTable;
import oncall.domain.Worker;
import oncall.domain.WorkingCalendar;
import oncall.dto.DateRequest;
import oncall.dto.TimeTableResponse;
import oncall.dto.WorkerNamesRequests;
import oncall.service.WorkerService;
import oncall.view.input.InputView;
import oncall.view.output.OutputView;

public class Controller {
    private final WorkerService workerService;
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(WorkerService workerService, InputView inputView, OutputView outputView) {
        this.workerService = workerService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        DateRequest dateRequest = inputView.receiveDate();
        WorkerNamesRequests workerNamesRequests = inputView.receiveWorkerName();

        List<Worker> weekdayWorkers = workerService.createWorkerList(workerNamesRequests.weekdayWorkers());
        List<Worker> holidayWorkers = workerService.createWorkerList(workerNamesRequests.holidayWorkers());

        WorkingCalendar workingCalendar = new WorkingCalendar(dateRequest.month(), dateRequest.dayOfWeek());

        TimeTable timeTable = TimeTable.from(workingCalendar);
        timeTable.updateTable(Month.of(dateRequest.month()), weekdayWorkers, holidayWorkers);

        TimeTableResponse timeTableResponse = workerService.showTimeTableOfThisMonth(timeTable);
        outputView.printTimeTable(timeTableResponse);
    }
}
