package christmas.controller;

import christmas.constants.menu.Menu;
import christmas.service.CustomerService;
import christmas.service.CustomerServiceFinder;
import christmas.view.inputview.WootecoPlannerInputView;
import christmas.view.outputview.WootecoPlannerOutputView;
import java.time.LocalDateTime;
import java.util.Map;

public record WootecoPlannerController(
        WootecoPlannerInputView wootecoPlannerInputView,
        WootecoPlannerOutputView wootecoPlannerOutputView
) implements PlannerControllable {
    public WootecoPlannerController() {
        this(new WootecoPlannerInputView(), new WootecoPlannerOutputView());
    }

    public void run() {
        CustomerService customerService = findServiceWithInput();
        service(customerService);
    }

    private CustomerService findServiceWithInput() {
        wootecoPlannerOutputView.printWelcomeMessage();
        LocalDateTime dateToVisit = wootecoPlannerInputView.receiveDateToVisit();
        Map<Menu, Integer> menuToOrder = wootecoPlannerInputView.receiveMenuToOrder();

        return CustomerServiceFinder.find(dateToVisit, menuToOrder);
    }

    private void service(CustomerService customerService) {
        notifyPreviewMessage(customerService);
        notifyOrderedMenu(customerService);
        notifyAmountBeforeDiscount(customerService);
        notifyFreeMenu(customerService);
        notifyDiscountDescription(customerService);
        notifyTotalDiscountAmount(customerService);
        notifyExpectedAmountToPay(customerService);
        notifyEventBadgeToGet(customerService);
    }

    private void notifyPreviewMessage(CustomerService customerService) {
        wootecoPlannerOutputView.printPreviewMessage(customerService.getDayOfMonth());
    }

    private void notifyOrderedMenu(CustomerService customerService) {
        wootecoPlannerOutputView.printOrderedMenu(customerService.showOrderedMenu());
    }

    private void notifyAmountBeforeDiscount(CustomerService customerService) {
        wootecoPlannerOutputView.printAmountBeforeDiscount(customerService.showAmountBeforeDiscount());
    }

    private void notifyFreeMenu(CustomerService customerService) {
        wootecoPlannerOutputView.printFreeMenu(customerService.showFreeMenu());
    }

    private void notifyDiscountDescription(CustomerService customerService) {
        wootecoPlannerOutputView.printDiscountDescription(customerService.showDiscountDescription());
    }

    private void notifyTotalDiscountAmount(CustomerService customerService) {
        wootecoPlannerOutputView.printTotalDiscountAmount(customerService.showTotalBenefitsAmount());
    }

    private void notifyExpectedAmountToPay(CustomerService customerService) {
        wootecoPlannerOutputView.printExpectedAmount(customerService.showExpectedAmountToPay());
    }

    private void notifyEventBadgeToGet(CustomerService customerService) {
        wootecoPlannerOutputView.printEventBadge(customerService.showEventBadge());
    }
}
