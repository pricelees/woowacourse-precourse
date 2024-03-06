package christmas.view.inputview;

import christmas.constants.menu.Menu;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Supplier;

public class WootecoPlannerInputView {
    private <T> T retryInputOnException(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println();
            return retryInputOnException(supplier);
        }
    }

    public LocalDateTime receiveDateToVisit() {
        final DateInputView dateInputView = new DateInputView();
        return retryInputOnException(dateInputView::receiveVisitDate);
    }

    public Map<Menu, Integer> receiveMenuToOrder() {
        final MenuInputView menuInputView = new MenuInputView();
        return retryInputOnException(menuInputView::receiveMenuAndCounts);
    }
}
