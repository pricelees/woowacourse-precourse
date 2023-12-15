package menu.config;

import menu.controller.MenuRecommendController;
import menu.service.CoachService;
import menu.service.MenuRecommendService;
import menu.view.input.ConsoleInputValidator;
import menu.view.input.InputView;
import menu.view.input.Reader;
import menu.view.output.OutputView;
import menu.view.output.Printer;

public final class AppConfig {
    private AppConfig() {
    }

    public static MenuRecommendController menuRecommendController() {
        return new MenuRecommendController(
                coachService(),
                menuRecommendService(),
                inputView(),
                outputView()
        );
    }

    private static CoachService coachService() {
        return new CoachService();
    }

    private static MenuRecommendService menuRecommendService() {
        return new MenuRecommendService();
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
