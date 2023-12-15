package menu.view.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import menu.dto.CoachNamesRequest;
import menu.dto.CoachRequest;
import menu.view.output.Printer;


public final class InputView {
    private static final String REQUEST_COACH_NAMES = "코치의 이름을 입력해 주세요. (, 로 구분)";
    private static final String REQUEST_MENU_CANT_EAT = "%s(이)가 못 먹는 메뉴를 입력해 주세요.";
    private static final String NAME_DELIMITER = ",";
    private final Reader reader;
    private final Printer printer;
    private final ConsoleInputValidator consoleInputValidator;

    public InputView(Reader reader, Printer printer, ConsoleInputValidator consoleInputValidator) {
        this.reader = reader;
        this.printer = printer;
        this.consoleInputValidator = consoleInputValidator;
    }

    public CoachNamesRequest receiveCoachNames() {
        return retryInputOnException(() -> {
            printer.print(REQUEST_COACH_NAMES);
            String input = reader.readLine();
            consoleInputValidator.validateCoachNames(input);

            return new CoachNamesRequest(Arrays.asList(input.split(NAME_DELIMITER)));
        });
    }

    public List<CoachRequest> receiveAllCoachesInfo(CoachNamesRequest coachNamesRequest) {
        return coachNamesRequest.coachNames().stream()
                .map(this::receiveMenusCantEat)
                .toList();
    }

    private CoachRequest receiveMenusCantEat(String coachName) {
        return retryInputOnException(() -> {
            printer.printFormat(REQUEST_MENU_CANT_EAT, coachName);
            String input = reader.readLine();
            consoleInputValidator.validateMenusCantEat(input);

            if (input.isBlank()) {
                return new CoachRequest(coachName, new ArrayList<>());
            }

            return new CoachRequest(coachName, new ArrayList<>(Arrays.asList(input.split(NAME_DELIMITER))));
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
