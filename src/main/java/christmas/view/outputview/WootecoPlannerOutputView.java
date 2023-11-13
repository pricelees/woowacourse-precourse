package christmas.view.outputview;

import christmas.constants.Constants;
import christmas.constants.view.OutputViewConstants;

public class WootecoPlannerOutputView {
    public void printWelcomeMessage() {
        System.out.println(OutputViewConstants.WELCOME_MESSAGE);
    }

    public void printPreviewMessage(int day) {
        System.out.printf(OutputViewConstants.PREVIEW_BENEFITS_MESSAGE + Constants.LINE_SEPARATOR, day);
        printEmptyLine();
    }

    public void printOrderedMenu(String orderedMenu) {
        System.out.println(OutputViewConstants.ORDERED_MENU);
        System.out.println(orderedMenu);
        printEmptyLine();
    }

    public void printAmountBeforeDiscount(String amount) {
        System.out.println(OutputViewConstants.TOTAL_AMOUNT_BEFORE_DISCOUNT);
        System.out.println(amount);
        printEmptyLine();
    }

    public void printFreeMenu(String freeMenu) {
        System.out.println(OutputViewConstants.MENU_FOR_FREE);
        System.out.println(freeMenu);
        printEmptyLine();
    }

    public void printDiscountDescription(String description) {
        System.out.println(OutputViewConstants.BENEFITS_DESCRIPTION);
        System.out.println(description);
        printEmptyLine();
    }

    public void printTotalDiscountAmount(String amount) {
        System.out.println(OutputViewConstants.TOTAL_BENEFITS_AMOUNT);
        System.out.println(amount);
        printEmptyLine();
    }

    public void printExpectedAmount(String amount) {
        System.out.println(OutputViewConstants.PRICE_AFTER_DISCOUNT);
        System.out.println(amount);
        printEmptyLine();
    }

    public void printEventBadge(String eventBadge) {
        System.out.println(OutputViewConstants.EVENT_BADGE);
        System.out.println(eventBadge);
    }

    public void printEmptyLine() {
        System.out.println();
    }
}
