package org.example.view.input;

public final class ConsoleInputValidator {
    private static ConsoleInputValidator instance;
    private ConsoleInputValidator() {
    }

    public static ConsoleInputValidator getInstance() {
        if (instance == null) {
            instance = new ConsoleInputValidator();
        }
        return instance;
    }
}
