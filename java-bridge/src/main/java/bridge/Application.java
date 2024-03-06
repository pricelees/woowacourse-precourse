package bridge;

import static bridge.config.AppConfig.bridgeGame;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        bridgeGame().start();
        Console.close();
    }
}
