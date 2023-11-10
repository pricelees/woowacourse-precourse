package christmas.discount;

import christmas.constants.Constants;
import christmas.domain.SelectedMenu;
import java.time.LocalDateTime;

public class ChampagneDiscount implements DiscountStrategy {
    private static final String DISCOUNT_TYPE = "증정 이벤트";
    private static final int MINIMUM_AMOUNT_TO_GET = 120_000;
    private static final int ONE_CHAMPAGNE_PRICE = 25_000;

    @Override
    public int getDiscountAmount(LocalDateTime date, SelectedMenu selectedMenu) {
        validateDate(date);
        int purchaseAmount = selectedMenu.getTotalAmountBeforeDiscount();
        if (purchaseAmount >= MINIMUM_AMOUNT_TO_GET) {
            return ONE_CHAMPAGNE_PRICE;
        }
        return Constants.ZERO;
    }

    @Override
    public void validateDate(LocalDateTime date) {
        validateYearAndMonth(date);
    }

    @Override
    public String getTypeName() {
        return DISCOUNT_TYPE;
    }
}
