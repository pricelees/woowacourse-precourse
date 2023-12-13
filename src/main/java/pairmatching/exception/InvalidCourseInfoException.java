package pairmatching.exception;

public class InvalidCourseInfoException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "[ERROR] 유효하지 않은 입력입니다.";
    public InvalidCourseInfoException() {
        super(ERROR_MESSAGE);
    }
}
