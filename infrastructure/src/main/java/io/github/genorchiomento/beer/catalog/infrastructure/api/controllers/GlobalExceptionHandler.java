package io.github.genorchiomento.beer.catalog.infrastructure.api.controllers;

import io.github.genorchiomento.beer.catalog.domain.exceptions.DomainException;
import io.github.genorchiomento.beer.catalog.domain.exceptions.NotFoundException;
import io.github.genorchiomento.beer.catalog.domain.validation.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(final NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiErrors.from(exception));
    }

    @ExceptionHandler(value = {DomainException.class})
    public ResponseEntity<?> handleDomainException(final DomainException exception) {
        return ResponseEntity.unprocessableEntity().body(ApiErrors.from(exception));
    }

    record ApiErrors(String message, List<Error> errors) {
        static ApiErrors from(final DomainException exception) {
            return new ApiErrors(exception.getMessage(), exception.getErrors());
        }
    }
}
