package christmas;

import christmas.controller.PlannerMainController;
import christmas.view.inputview.PlannerInputView;
import christmas.view.outputview.PlannerOutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        PlannerInputView plannerInputView = new PlannerInputView();
        PlannerOutputView plannerOutputView = new PlannerOutputView();
        PlannerMainController plannerMainController = new PlannerMainController(
                plannerInputView,
                plannerOutputView
        );

        plannerMainController.start();
    }
}