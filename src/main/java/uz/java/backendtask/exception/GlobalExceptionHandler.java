package uz.java.backendtask.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.java.backendtask.dto.ResponseExceptionDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({
            AuthenticationException.class,
            UnauthorizedException.class
    })
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

    @ExceptionHandler({
            AdsCampaignException.class,
            AdsCreativeException.class,
            AdsPlacementException.class,
            AssigmentException.class,
            CategoryException.class,
            ConflictException.class,
            FileException.class,
            MediaException.class,
            NewsException.class,
            RoleException.class,
            TagException.class,
            UserException.class,
            UserRoleException.class,
            ValidationException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ResponseExceptionDTO> handleAppException(
            Exception ex
    ) {
        return ResponseEntity.status(400).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(400)
                        .message(ex.getMessage())
                        .build());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseExceptionDTO> handleBusinessException(
            MethodArgumentNotValidException ex
    ) {
        String message;

        if (!ex.getBindingResult().getFieldErrors().isEmpty()) {
            message = ex.getBindingResult()
                    .getFieldErrors()
                    .get(0)
                    .getDefaultMessage();
        } else {
            message = ex.getBindingResult()
                    .getGlobalErrors()
                    .get(0)
                    .getDefaultMessage();
        }

        return ResponseEntity.status(400).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(400)
                        .message(message)
                        .build()
        );
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


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseExceptionDTO> handleBusinessException(
            Exception ex
    ) {
        ex.printStackTrace();
        return ResponseEntity.status(500).body(
                ResponseExceptionDTO.builder()
                        .ok(false)
                        .status(500)
                        .message(ex.getMessage())
                        .build());
    }


}
