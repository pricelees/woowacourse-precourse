package christmas.util;

import java.text.DecimalFormat;

public class PriceFormatter {
    private static final DecimalFormat PRICE_FORMATTER = new DecimalFormat("###,###원");

    public static String format(int amount) {
        return PRICE_FORMATTER.format(amount);
    }
}
