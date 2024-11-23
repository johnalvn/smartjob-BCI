package cl.bci.bciapi.exception;

import cl.bci.bciapi.controller.AppUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@ControllerAdvice
public class CustomExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        logger.error("Excepción capturada: {}", e.getMessage(), e);
        return new ResponseEntity<>(Collections.singletonMap("mensaje", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
