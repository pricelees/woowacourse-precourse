package christmas.controller;

import christmas.constants.menu.Menu;
import christmas.domain.SelectedMenu;
import christmas.domain.WootecoPlanner;
import christmas.view.inputview.PlannerInputView;
import christmas.view.outputview.PlannerOutputView;
import java.time.LocalDateTime;
import java.util.Map;

public record PlannerMainController(PlannerInputView plannerInputView, PlannerOutputView plannerOutputView) {
    public void start() {
        plannerOutputView.printWelcomeMessage();
        LocalDateTime visitDate = plannerInputView.receiveDateToVisit();
        Map<Menu, Integer> menuToOrder = plannerInputView.receiveMenuToOrder();
        plannerOutputView.printPreviewMessage(visitDate.getDayOfMonth());

        SelectedMenu selectedMenu = new SelectedMenu(menuToOrder);
        WootecoPlanner wootecoPlanner = WootecoPlanner.valueOf(visitDate, selectedMenu);

        printAllInfo(wootecoPlanner);
    }

    private void printAllInfo(WootecoPlanner wootecoPlanner) {
        plannerOutputView.printOrderedMenu(wootecoPlanner.showOrderedMenu());
        plannerOutputView.printAmountBeforeDiscount(wootecoPlanner.showAmountBeforeDiscount());
        plannerOutputView.printFreeMenu(wootecoPlanner.showFreeMenu());
        plannerOutputView.printDiscountDescription(wootecoPlanner.showDiscountDescription());
        plannerOutputView.printTotalDiscountAmount(wootecoPlanner.showTotalBenefitsAmount());
        plannerOutputView.printExpectedAmount(wootecoPlanner.showExpectedAmountToPay());
        plannerOutputView.printEventBadge(wootecoPlanner.showEventBadge());
    }
}
