package org.myschool.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
        return buildResponse(HttpStatus.BAD_REQUEST, "La requête contient des champs invalides", request, fieldErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException exception, HttpServletRequest request) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        exception.getConstraintViolations().forEach(violation -> fieldErrors.put(violation.getPropertyPath().toString(), violation.getMessage()));
        return buildResponse(HttpStatus.BAD_REQUEST, "La requête contient des champs invalides", request, fieldErrors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusinessRule(BusinessRuleException exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentials(InvalidCredentialsException exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.UNAUTHORIZED, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiError> handleBadRequest(Exception exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "La requête est invalide", request, Map.of());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, "Cette opération est impossible car la ressource est déjà utilisée", request, Map.of());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, "Vous n'avez pas les droits nécessaires pour cette opération", request, Map.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedException(Exception exception, HttpServletRequest request) {
        LOGGER.error("Erreur non gérée pour {}", request.getRequestURI(), exception);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue", request, Map.of());
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message, HttpServletRequest request, Map<String, String> fieldErrors) {
        ApiError error = new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message, request.getRequestURI(), fieldErrors);
        return ResponseEntity.status(status).body(error);
    }
}
