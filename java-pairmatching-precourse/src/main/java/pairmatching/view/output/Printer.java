package pairmatching.view.output;

import java.util.List;

public class Printer {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void print(Object input) {
        System.out.println(input);
    }

    public void printEmptyLine() {
        System.out.print(LINE_SEPARATOR);
    }

    public void printException(String exceptionMessage) {
        System.out.println(exceptionMessage);
        printEmptyLine();
    }
}
