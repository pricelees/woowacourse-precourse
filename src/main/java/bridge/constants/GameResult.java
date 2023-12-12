package bridge.constants;

public enum GameResult {
    SUCCEED("성공"),
    FAIL("실패");
    private final String result;

    GameResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
