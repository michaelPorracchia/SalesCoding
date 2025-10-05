
package fr.itsf.sales.exception;

import fr.itsf.model.ErrorResponseSpec;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseSpec> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseSpec error = new ErrorResponseSpec();
        error.setCode("400");
        error.setMessage("Bad Request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseSpec> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorResponseSpec error = new ErrorResponseSpec();
        error.setCode("412");
        error.setMessage("Precondition Failed");
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseSpec> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponseSpec error = new ErrorResponseSpec();
        error.setCode("412");
        error.setMessage("Precondition Failed");
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseSpec> handleGlobalException(Exception ex) {
        ErrorResponseSpec error = new ErrorResponseSpec();
        error.setCode("500");
        error.setMessage("Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}