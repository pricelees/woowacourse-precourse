package pairmatching.exception;

public class InvalidOptionException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "[ERROR] 유효하지 않은 입력입니다.";
    public InvalidOptionException() {
        super(ERROR_MESSAGE);
    }
}
