package pairmatching.view.output;

import pairmatching.dto.MatchResponse;
import pairmatching.view.input.InputMessages;

public class OutputView {
    private static final String MATCHING_RESULT_MESSAGE = "페어 매칭 결과입니다.";
    private static final String RESET_MESSAGE = "초기화 되었습니다.";
    private static final String COLON_WITH_SPACE = " : ";
    private final Printer printer;

    public OutputView(Printer printer) {
        this.printer = printer;
    }

    public void printMatchInfo(MatchResponse matchResponse) {
        printer.print(MATCHING_RESULT_MESSAGE);
        matchResponse.pairResponses()
                .forEach(pairResponse -> printer.print(String.join(COLON_WITH_SPACE, pairResponse.crewNames())));
        printer.printEmptyLine();
    }

    public void printResetMessage() {
        printer.print(RESET_MESSAGE);
    }

    public void printCourse() {
        printer.print(InputMessages.displayAllCourseInfo());
    }

    public void printExceptionMessage(String message) {
        printer.print(message);
        printer.printEmptyLine();
    }
}
