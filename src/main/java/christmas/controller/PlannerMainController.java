package christmas.controller;

import christmas.constants.menu.Menu;
import christmas.domain.Customer;
import christmas.domain.SelectedMenu;
import christmas.domain.WootecoPlanner;
import christmas.view.inputview.PlannerInputView;
import christmas.view.outputview.PlannerOutputView;
import java.time.LocalDateTime;
import java.util.Map;

public record PlannerMainController(PlannerInputView plannerInputView, PlannerOutputView plannerOutputView) {
    public void start() {
        Customer customer = escortCustomer();
        WootecoPlanner wootecoPlanner = WootecoPlanner.valueOf(customer);

        printAllInfo(wootecoPlanner);
    }

    private Customer escortCustomer() {
        plannerOutputView.printWelcomeMessage();
        Customer customer = receiveCustomerInfo();
        plannerOutputView.printPreviewMessage(customer.dayOfMonthToVisit());

        return customer;
    }

    private Customer receiveCustomerInfo() {
        LocalDateTime dateToVisit = plannerInputView.receiveDateToVisit();
        Map<Menu, Integer> menuToOrder = plannerInputView.receiveMenuToOrder();

        return new Customer(dateToVisit, new SelectedMenu(menuToOrder));
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
