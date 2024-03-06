package menu.view.input;

import camp.nextstep.edu.missionutils.Console;

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
