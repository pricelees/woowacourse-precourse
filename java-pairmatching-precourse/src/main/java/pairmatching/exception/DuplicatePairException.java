package pairmatching.exception;

public class DuplicatePairException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "[ERROR] 중복되는 페어가 존재합니다.";

    public DuplicatePairException() {
        super(ERROR_MESSAGE);
    }
}
