package bridge.constants;

public enum MovingResult {
    SUCCEED(" O "),
    FALL(" X ");

    private final String symbol;

    MovingResult(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
