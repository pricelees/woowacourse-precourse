package pairmatching.exception;

public class NotExistMatchingException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "[ERROR] 매칭 이력이 없습니다.";
    public NotExistMatchingException() {
        super(ERROR_MESSAGE);
    }
}
