package bridge.view.output;

public class Printer {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void print(Object input) {
        System.out.println(input);
    }

    public void printFormat(String format, Object value) {
        System.out.printf(format, value);
        printEmptyLine();
    }

    public void printEmptyLine() {
        System.out.print(LINE_SEPARATOR);
    }

    public static void printException(String exceptionMessage) {
        System.out.println(exceptionMessage);
    }
}
