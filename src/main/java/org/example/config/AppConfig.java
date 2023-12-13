package org.example.config;

import org.example.view.input.ConsoleInputValidator;
import org.example.view.input.InputView;
import org.example.view.input.Reader;
import org.example.view.output.OutputView;
import org.example.view.output.Printer;

public final class AppConfig {
    private AppConfig() {
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
