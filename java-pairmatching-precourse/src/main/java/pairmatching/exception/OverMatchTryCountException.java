package pairmatching.exception;

public class OverMatchTryCountException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "[ERROR] 매칭 가능 정보가 없습니다.";
    public OverMatchTryCountException() {
        super(ERROR_MESSAGE);
    }
}
