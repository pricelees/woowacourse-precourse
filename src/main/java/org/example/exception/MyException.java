package org.example.exception;

public class MyException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "[ERROR] 안녕하세요.";

    public MyException() {
        super(ERROR_MESSAGE);
    }

    // test
    public static void main(String[] args) {
        try {
            throw new MyException();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
