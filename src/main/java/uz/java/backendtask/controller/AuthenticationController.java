package uz.java.backendtask.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.java.backendtask.dto.AuthenticationAccessResponseDTO;
import uz.java.backendtask.dto.AuthenticationRefreshResponseDTO;
import uz.java.backendtask.dto.AuthenticationRequestDTO;
import uz.java.backendtask.security.SecurityUser;
import uz.java.backendtask.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/admin/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationAccessResponseDTO> login(@Valid @RequestBody AuthenticationRequestDTO requestDTO) {
        return ResponseEntity.ok(service.login(requestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationRefreshResponseDTO> refresh(@AuthenticationPrincipal SecurityUser securityUser, HttpServletRequest request) {
        return ResponseEntity.ok(service.refresh(securityUser, request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal SecurityUser securityUser) {
        service.logout(securityUser);
        return ResponseEntity.noContent().build();
    }
}
