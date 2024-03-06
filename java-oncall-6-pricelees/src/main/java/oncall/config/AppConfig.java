package oncall.config;

import oncall.controller.Controller;
import oncall.service.WorkerService;
import oncall.view.input.ConsoleInputValidator;
import oncall.view.input.InputView;
import oncall.view.input.Reader;
import oncall.view.output.OutputView;
import oncall.view.output.Printer;

public final class AppConfig {
    private AppConfig() {
    }

    public static Controller controller() {
        return new Controller(workerService(), inputView(), outputView());
    }

    private static WorkerService workerService() {
        return new WorkerService();
    }

    private static InputView inputView() {
        return new InputView(reader(), printer(), inputValidator());
    }

    private static OutputView outputView() {
        return new OutputView(printer());
    }

    private static Reader reader() {
        return Reader.getInstance();
    }

    private static Printer printer() {
        return Printer.getInstance();
    }

    private static ConsoleInputValidator inputValidator() {
        return ConsoleInputValidator.getInstance();
    }
}
