package org.example.view.input;

public final class Reader {
    private static Reader instance;

    private Reader() {
    }

    public static Reader getInstance() {
        if (instance == null) {
            instance = new Reader();
        }
        return instance;
    }

    public String readLine() {
        return Console.readLine();
    }
}
