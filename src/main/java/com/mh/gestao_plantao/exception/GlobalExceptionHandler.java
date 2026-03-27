package com.mh.gestao_plantao.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(RuntimeException ex, Model model) {
        model.addAttribute("erro", ex.getMessage());
        return "erro";
    }
}
