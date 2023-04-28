package mate.cafecatalog.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(this::getErrorMessages)
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(DataProcessingException.class)
    protected ResponseEntity<Object> handleDataException(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        int status = 500;
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status);
        body.put("error", ex.getMessage());
        return handleExceptionInternal(ex, body, new HttpHeaders(),
                HttpStatus.valueOf(status), request);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        int status = 500;
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status);
        body.put("error", ex.getMessage());
        return handleExceptionInternal(ex, body, new HttpHeaders(),
                HttpStatus.valueOf(status), request);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    protected ResponseEntity<Object> handleInvalidJwtException(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        int status = 500;
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status);
        body.put("error", ex.getMessage());
        return handleExceptionInternal(ex, body, new HttpHeaders(),
                HttpStatus.valueOf(status), request);
    }

    private String getErrorMessages(ObjectError error) {
        if (error instanceof FieldError) {
            String field = ((FieldError) error).getField();
            return field + " " + error.getDefaultMessage();
        }
        return error.getDefaultMessage();
    }
}
