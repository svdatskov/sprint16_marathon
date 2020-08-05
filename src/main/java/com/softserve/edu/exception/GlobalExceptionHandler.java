package com.softserve.edu.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleMyCustomException(Exception exception) {
        ModelAndView model = new ModelAndView("error_page");
        model.addObject("info", exception.getMessage());
        model.setStatus(HttpStatus.BAD_REQUEST);
        return model;
    }


}
