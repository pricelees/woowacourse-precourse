package christmas.discount;

import christmas.constants.Constants;
import christmas.constants.time.EventTime;
import christmas.domain.Customer;
import christmas.domain.DateToVisit;
import christmas.util.PriceFormatter;
import java.time.LocalDateTime;

public final class ChristmasDiscount implements DiscountStrategy {
    private static final ChristmasDiscount INSTANCE = new ChristmasDiscount();
    private static final String DISCOUNT_TYPE = "크리스마스 디데이 할인";
    private static final LocalDateTime EVENT_START_DATE = LocalDateTime.of(
            EventTime.YEAR, EventTime.MONTH, EventTime.DAY_OF_MONTH_EVENTS_START,
            EventTime.HOUR, EventTime.MINUTE, EventTime.SECOND);
    private static final int DEFAULT_DISCOUNT_AMOUNT = 1000;
    private static final int DISCOUNT_AMOUNT_PER_DAY = 100;

    private ChristmasDiscount() {
    }

    public static ChristmasDiscount getInstance() {
        return INSTANCE;
    }

    public int getDiscountAmount(final Customer customer) {
        final DateToVisit dateToVisit = customer.dateToVisit();
        if (dateToVisit.isBeforeChristmas()) {
            final int totalDay = dateToVisit.dayOfMonth() - EVENT_START_DATE.getDayOfMonth();
            return -(DEFAULT_DISCOUNT_AMOUNT + (DISCOUNT_AMOUNT_PER_DAY * totalDay));
        }

        return Constants.ZERO;
    }

    @Override
    public String getDescription(final Customer customer) {
        return DISCOUNT_TYPE + Constants.COLON_WITH_SPACE + PriceFormatter.format(getDiscountAmount(customer));
    }
}
