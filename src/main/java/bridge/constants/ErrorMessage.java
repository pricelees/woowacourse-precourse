package bridge.constants;

public enum ErrorMessage {
    INVALID_BRIDGE_SIZE("다리 길이는 3부터 20 사이의 숫자여야 합니다."),
    INVALID_LOCATION("이동할 칸은 U(위) 또는 D(아래)만 입력 가능합니다."),
    INVALID_GAME_COMMAND("재시작 여부는 R(재시작) 또는 Q(종료)만 입력 가능합니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";
    private final String errorMessgage;

    ErrorMessage(String errorMessgage) {
        this.errorMessgage = errorMessgage;
    }

    public String getErrorMessgage() {
        return ERROR_PREFIX + errorMessgage;
    }
}
