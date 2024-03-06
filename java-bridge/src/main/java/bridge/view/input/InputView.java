package bridge.view.input;

import bridge.view.output.Printer;
import java.util.function.Supplier;

/**
 * 사용자로부터 입력을 받는 역할을 한다.
 */
public class InputView {
    private static final String REQUEST_BRIDGE_SIZE = "다리의 길이를 입력해주세요";
    private static final String REQUEST_LOCATION_TO_MOVE = "이동할 칸을 선택해주세요. (위: U, 아래: D)";
    private static final String REQUEST_GAME_COMMAND = "게임을 다시 시도할지 여부를 입력해주세요. (재시도: R, 종료: Q)";
    private final Reader reader;
    private final Printer printer;
    private final ConsoleInputValidator consoleInputValidator;

    public InputView(Reader reader, Printer printer, ConsoleInputValidator consoleInputValidator) {
        this.reader = reader;
        this.printer = printer;
        this.consoleInputValidator = consoleInputValidator;
    }

    /**
     * 다리의 길이를 입력받는다.
     */
    public int readBridgeSize() {
        return retryInputOnException(() -> {
            printer.print(REQUEST_BRIDGE_SIZE);
            String input = reader.readLine();
            consoleInputValidator.validateBridgeSizeFormat(input);
            consoleInputValidator.validateBridgeSize(Integer.parseInt(input));

            return Integer.parseInt(input);
        });
    }

    /**
     * 사용자가 이동할 칸을 입력받는다.
     */
    public String readMoving() {
        return retryInputOnException(() -> {
            printer.print(REQUEST_LOCATION_TO_MOVE);
            String input = reader.readLine();
            consoleInputValidator.validateLocationToMove(input);
            return input;
        });
    }

    /**
     * 사용자가 게임을 다시 시도할지 종료할지 여부를 입력받는다.
     */
    public String readGameCommand() {
        return retryInputOnException(() -> {
            printer.print(REQUEST_GAME_COMMAND);
            String input = reader.readLine();
            consoleInputValidator.validateRestartOption(input);

            return input;
        });
    }

    private <T> T retryInputOnException(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            Printer.printException(e.getMessage());
            return retryInputOnException(supplier);
        }
    }
}
