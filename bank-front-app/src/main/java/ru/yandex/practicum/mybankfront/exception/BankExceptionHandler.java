package ru.yandex.practicum.mybankfront.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BankExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", "Сервис временно недоступен");
        return "error";
    }

    @ExceptionHandler(BankException.class)
    public String handleException(BankException ex, Model model) {
        model.addAttribute("message", ex.getErrorMessage());
        return "error";
    }
}
