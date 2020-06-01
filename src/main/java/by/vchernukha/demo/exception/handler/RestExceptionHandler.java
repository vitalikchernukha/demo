package by.vchernukha.demo.exception.handler;

import by.vchernukha.demo.exception.NegativeBalanceException;
import by.vchernukha.demo.exception.NotFoundEntityException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logger.info(ex.getClass().getName());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NotFoundEntityException.class, NegativeBalanceException.class})
    protected ResponseEntity<Object> handleCommonException(Exception ex, WebRequest request) {
        logger.info(ex.getClass().getName());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());

        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}