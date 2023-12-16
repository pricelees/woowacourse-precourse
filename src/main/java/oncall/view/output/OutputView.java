package oncall.view.output;

import oncall.dto.TimeResponse;
import oncall.dto.TimeTableResponse;

public final class OutputView {
    private final Printer printer;
    private static final String TIME_TABLE_FORMAT = "%d월 %d일 %s %s";

    public OutputView(Printer printer) {
        this.printer = printer;
    }

    public void printTimeTable(TimeTableResponse timeTableResponse) {
        timeTableResponse.timeResponses().forEach(this::printTime);
    }

    private void printTime(TimeResponse timeResponse) {
        int month = timeResponse.month();
        int dayOfMonth = timeResponse.dayOfMonth();
        String dayOfWeek = timeResponse.dayOfWeek();
        String workerName = timeResponse.workerName();

        printer.printFormat(TIME_TABLE_FORMAT, month, dayOfMonth, dayOfWeek, workerName);
    }

    public void printExceptionMessage(String message) {
        printer.println(message);
        printer.printEmptyLine();
    }
}
