package oncall.view.input;

import java.util.Arrays;
import java.util.function.Supplier;
import oncall.dto.DateRequest;
import oncall.dto.WorkerNamesRequests;
import oncall.dto.WorkerNamesRequests.WorkerNamesRequest;
import oncall.view.output.Printer;

public final class InputView {
    private static final String DELIMITER_FOR_ALL = ",";
    private static final int MONTH_INDEX = 0;
    private static final int START_DAY_INDEX = 1;
    private static final String REQUEST_DATE = "비상 근무를 배정할 월과 시작 요일을 입력하세요> ";
    private static final String REQUEST_WEEKDAY_WORKER = "평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ";
    private static final String REQUEST_WEEKEND_WORKER = "주말 비상 근무 순번대로 사원 닉네임을 입력하세요> ";
    private final Reader reader;
    private final Printer printer;
    private final ConsoleInputValidator consoleInputValidator;

    public InputView(Reader reader, Printer printer, ConsoleInputValidator consoleInputValidator) {
        this.reader = reader;
        this.printer = printer;
        this.consoleInputValidator = consoleInputValidator;
    }

    public DateRequest receiveDate() {
        return retryInputOnException(() -> {
            printer.print(REQUEST_DATE);
            String input = reader.readLine();
            consoleInputValidator.validateDateFormat(input);
            String[] monthAndStartday = input.split(DELIMITER_FOR_ALL);
            int month = Integer.parseInt(monthAndStartday[MONTH_INDEX]);
            String startDayOfWeek = monthAndStartday[START_DAY_INDEX];

            return new DateRequest(month, startDayOfWeek);
        });
    }

    public WorkerNamesRequests receiveWorkerName() {
        return retryInputOnException(() -> {
            WorkerNamesRequest weekdayWorker = receiveWorkerNames(REQUEST_WEEKDAY_WORKER);
            WorkerNamesRequest holidayWorker = receiveWorkerNames(REQUEST_WEEKEND_WORKER);
            printer.printEmptyLine();

            return new WorkerNamesRequests(weekdayWorker, holidayWorker);
        });
    }

    public WorkerNamesRequest receiveWorkerNames(String requestMessage) {
        printer.print(requestMessage);
        String input = reader.readLine();
        consoleInputValidator.validateWorkerNameFormat(input);

        return new WorkerNamesRequest(
                Arrays.stream(input.split(DELIMITER_FOR_ALL))
                        .toList()
        );
    }

    private <T> T retryInputOnException(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            printer.printException(e.getMessage());
            return retryInputOnException(supplier);
        }
    }
}
