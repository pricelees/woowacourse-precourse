package pairmatching.view.input;

import java.util.function.Supplier;
import pairmatching.dto.CourseInfoRequest;
import pairmatching.dto.ProgramOptionRequest;
import pairmatching.util.CourseInfoRequestMapper;
import pairmatching.util.ProgramOptionRequestMapper;
import pairmatching.view.output.Printer;

public class InputView {
    private final Reader reader;
    private final Printer printer;
    private final ConsoleInputValidator consoleInputValidator;

    public InputView(Reader reader, Printer printer, ConsoleInputValidator consoleInputValidator) {
        this.reader = reader;
        this.printer = printer;
        this.consoleInputValidator = consoleInputValidator;
    }

    public ProgramOptionRequest requestOption() {
        return retryInputOnException(() -> {
            printer.print(InputMessages.PROGRAM_OPTION_MESSAGE);
            String option = reader.readLine();
            consoleInputValidator.validateOption(option);

            return ProgramOptionRequestMapper.from(option);
        });
    }

    public CourseInfoRequest requestCourseInfo() {
        return retryInputOnException(() -> {
            printer.print(InputMessages.SELECT_COURSE_INFO_MESSAGE);
            String courseInfo = reader.readLine();
            consoleInputValidator.validateCourseInfo(courseInfo);

            return CourseInfoRequestMapper.from(courseInfo);
        });
    }

    public String requestRematchOption() {
        return retryInputOnException(() -> {
            printer.print(InputMessages.REMATCH_MESSAGE);
            String rematchOption = reader.readLine();
            consoleInputValidator.validateRematchOption(rematchOption);

            return rematchOption;
        });
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
