package uz.java.backendtask.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import uz.java.backendtask.dto.ResponseExceptionDTO;

import java.io.OutputStream;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        OutputStream out = response.getOutputStream();
        objectMapper.writeValue(out,
                ResponseExceptionDTO
                        .builder()
                        .ok(false)
                        .status(403)
                        .message("Forbidden")
                        .build());
        out.flush();
        out.close();
    }
}