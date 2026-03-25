package ru.yandex.practicum.mybankfront.exception;

public class BankException extends Exception {
    private final String errorMessage;

    public BankException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
