package org.example.view.input;

import java.util.function.Supplier;
import org.example.view.output.Printer;

public final class InputView {
    private final Reader reader;
    private final Printer printer;
    private final ConsoleInputValidator consoleInputValidator;

    public InputView(Reader reader, Printer printer, ConsoleInputValidator consoleInputValidator) {
        this.reader = reader;
        this.printer = printer;
        this.consoleInputValidator = consoleInputValidator;
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
