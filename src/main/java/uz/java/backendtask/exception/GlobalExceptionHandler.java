package uz.java.backendtask.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.java.backendtask.dto.ResponseExceptionDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseExceptionDTO> handleBusinessException(
            AuthenticationException ex
    ) {
        return ResponseEntity.status(401).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(401)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseExceptionDTO> handleBusinessException(
            UserException ex
    ) {
        return ResponseEntity.status(404).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(404)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<ResponseExceptionDTO> handleBusinessException(
            RoleException ex
    ) {
        return ResponseEntity.status(404).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(404)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(UserRoleException.class)
    public ResponseEntity<ResponseExceptionDTO> handleBusinessException(
            UserRoleException ex
    ) {
        return ResponseEntity.status(404).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(404)
                        .message(ex.getMessage())
                        .build());
    }


    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ResponseExceptionDTO> handleBusinessException(
            AuthorizationDeniedException ex
    ) {
        return ResponseEntity.status(403).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(403)
                        .message("Forbidden")
                        .build());
    }

    @ExceptionHandler(TagException.class)
    public ResponseEntity<ResponseExceptionDTO> handleBusinessException(
            TagException ex
    ) {
        return ResponseEntity.status(404).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(404)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<ResponseExceptionDTO> handleBusinessException(
            FileException ex
    ) {
        return ResponseEntity.status(404).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(404)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseExceptionDTO> handleBusinessException(
            Exception ex
    ) {
        return ResponseEntity.status(500).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(500)
                        .message(ex.getMessage())
                        .build());
    }


}
