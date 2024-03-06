package christmas.controller;

import christmas.constants.menu.Menu;
import christmas.service.CustomerService;
import christmas.service.CustomerServiceFinder;
import christmas.view.inputview.WootecoPlannerInputView;
import christmas.view.outputview.WootecoPlannerOutputView;
import java.time.LocalDateTime;
import java.util.Map;

public final class WootecoPlannerController implements PlannerControllable {
    private static final WootecoPlannerController INSTANCE = new WootecoPlannerController();
    private final WootecoPlannerInputView wootecoPlannerInputView = new WootecoPlannerInputView();
    private final WootecoPlannerOutputView wootecoPlannerOutputView = new WootecoPlannerOutputView();
    private CustomerService customerService;

    private WootecoPlannerController() {
    }

    public static WootecoPlannerController getInstance() {
        return INSTANCE;
    }

    public void run() {
        customerService = findServiceWithInput();
        service();
    }

    private CustomerService findServiceWithInput() {
        wootecoPlannerOutputView.printWelcomeMessage();
        LocalDateTime dateToVisit = wootecoPlannerInputView.receiveDateToVisit();
        Map<Menu, Integer> menuToOrder = wootecoPlannerInputView.receiveMenuToOrder();

        return CustomerServiceFinder.find(dateToVisit, menuToOrder);
    }

    private void service() {
        notifyPreviewMessage();
        notifyOrderedMenu();
        notifyAmountBeforeDiscount();
        notifyFreeMenu();
        notifyDiscountDescription();
        notifyTotalDiscountAmount();
        notifyExpectedAmountToPay();
        notifyEventBadgeToGet();
    }

    private void notifyPreviewMessage() {
        wootecoPlannerOutputView.printPreviewMessage(customerService.getDayOfMonth());
    }

    private void notifyOrderedMenu() {
        wootecoPlannerOutputView.printOrderedMenu(customerService.showOrderedMenu());
    }

    private void notifyAmountBeforeDiscount() {
        wootecoPlannerOutputView.printAmountBeforeDiscount(customerService.showAmountBeforeDiscount());
    }

    private void notifyFreeMenu() {
        wootecoPlannerOutputView.printFreeMenu(customerService.showFreeMenu());
    }

    private void notifyDiscountDescription() {
        wootecoPlannerOutputView.printDiscountDescription(customerService.showDiscountDescription());
    }

    private void notifyTotalDiscountAmount() {
        wootecoPlannerOutputView.printTotalDiscountAmount(customerService.showTotalBenefitsAmount());
    }

    private void notifyExpectedAmountToPay() {
        wootecoPlannerOutputView.printExpectedAmount(customerService.showExpectedAmountToPay());
    }

    private void notifyEventBadgeToGet() {
        wootecoPlannerOutputView.printEventBadge(customerService.showEventBadge());
    }
}
