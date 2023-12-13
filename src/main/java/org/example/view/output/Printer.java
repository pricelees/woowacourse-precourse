package org.example.view.output;

public final class Printer {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void print(Object input) {
        System.out.println(input);
    }

    public void printFormat(String format, Object value) {
        String formatWithNewLine = format + LINE_SEPARATOR;
        System.out.printf(formatWithNewLine, value);
    }

    public void printFormat(String format, Object... args) {
        String formatWithNewLine = format + LINE_SEPARATOR;
        System.out.printf(formatWithNewLine, args);
    }

    public void printEmptyLine() {
        System.out.println();
    }

    public void printException(String exceptionMessage) {
        System.out.println(exceptionMessage);
        printEmptyLine();
    }
}
