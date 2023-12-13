package org.example.view.output;

public final class OutputView {
    private final Printer printer;

    public OutputView(Printer printer) {
        this.printer = printer;
    }


    public void printExceptionMessage(String message) {
        printer.print(message);
        printer.printEmptyLine();
    }
}
