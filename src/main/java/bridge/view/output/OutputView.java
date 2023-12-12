package bridge.view.output;

import bridge.dto.CurrentMapResponse;
import bridge.dto.GameResultResponse;
import java.util.List;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {
    private static final String START_MESSAGE = "다리 건너기 게임을 시작합니다.";
    private static final String FINAL_RESULT_PREFIX = "최종 게임 결과";
    private static final String SUCCESS_OR_FAIL_FORMAT = "게임 성공 여부: %s";
    private static final String TRY_COUNT_FORMAT = "총 시도한 횟수: %d";
    private static final String LEFT_BRACE = "[";
    private static final String RIGHT_BRACE = "]";
    private static final String BAR = "|";

    private final Printer printer;

    public OutputView(Printer printer) {
        this.printer = printer;
    }

    public void printStartMessage() {
        printer.print(START_MESSAGE);
        printer.printEmptyLine();
    }

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printMap(CurrentMapResponse currentMapResponse) {
        printSection(currentMapResponse.upside());
        printSection(currentMapResponse.downSide());
        printer.printEmptyLine();
    }

    private void printSection(List<String> side) {
        printer.print(LEFT_BRACE + String.join(BAR, side) + RIGHT_BRACE);
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printResult(CurrentMapResponse currentMapResponse,
                            GameResultResponse gameResultResponse) {
        printer.print(FINAL_RESULT_PREFIX);
        printMap(currentMapResponse);
        printer.printFormat(SUCCESS_OR_FAIL_FORMAT, gameResultResponse.gameResult());
        printer.printFormat(TRY_COUNT_FORMAT, gameResultResponse.playCount());
    }
}
