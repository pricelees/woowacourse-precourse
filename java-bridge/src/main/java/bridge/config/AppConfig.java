package bridge.config;

import bridge.controller.BridgeGame;
import bridge.view.input.ConsoleInputValidator;
import bridge.view.input.InputView;
import bridge.view.input.Reader;
import bridge.view.output.OutputView;
import bridge.view.output.Printer;

public final class AppConfig {
    private AppConfig() {
    }

    public static BridgeGame bridgeGame() {
        return new BridgeGame(
                inputView(),
                outputView()
        );
    }

    private static InputView inputView() {
        return new InputView(reader(), printer(), inputValidator());
    }

    private static OutputView outputView() {
        return new OutputView(printer());
    }

    private static Reader reader() {
        return new Reader();
    }

    private static Printer printer() {
        return new Printer();
    }

    private static ConsoleInputValidator inputValidator() {
        return new ConsoleInputValidator();
    }
}
