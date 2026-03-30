package com.mh.gestao_plantao.exception.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(NoSuchElementException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "erro";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleConflict(IllegalArgumentException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "erro";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleBusiness(IllegalStateException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "erro";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleGeneric(RuntimeException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "erro";
    }
}
