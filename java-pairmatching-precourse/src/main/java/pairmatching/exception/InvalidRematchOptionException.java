package pairmatching.exception;

public class InvalidRematchOptionException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "[ERROR] 유효하지 않은 입력입니다.";
    public InvalidRematchOptionException() {
        super(ERROR_MESSAGE);
    }
}
