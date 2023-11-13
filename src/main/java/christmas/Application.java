package christmas;

import camp.nextstep.edu.missionutils.Console;
import christmas.controller.MainController;
import christmas.controller.WootecoPlannerController;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        MainController mainController = new MainController(new WootecoPlannerController());
        mainController.run();
        Console.close();
    }
}