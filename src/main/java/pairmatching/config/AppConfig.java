package pairmatching.config;

import pairmatching.controller.FrontController;
import pairmatching.repository.PairsRepository;
import pairmatching.service.PairMatchingService;
import pairmatching.service.PairSearchingService;
import pairmatching.service.PairService;
import pairmatching.view.input.ConsoleInputValidator;
import pairmatching.view.input.InputView;
import pairmatching.view.input.Reader;
import pairmatching.view.output.OutputView;
import pairmatching.view.output.Printer;

public class AppConfig {
    public static FrontController frontController() {
        return new FrontController(pairService(), inputView(), outputView());
    }
    private static PairService pairService() {
        return new PairService(pairsRepository(), pairMatchingService(), pairSearchingService());
    }
    private static PairsRepository pairsRepository() {
        return new PairsRepository();
    }
    private static PairMatchingService pairMatchingService() {
        return new PairMatchingService();
    }
    private static PairSearchingService pairSearchingService() {
        return new PairSearchingService();
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
