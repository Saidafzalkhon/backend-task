package uz.java.backendtask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.java.backendtask.dto.UserCriteria;
import uz.java.backendtask.dto.UserResponseDTO;
import uz.java.backendtask.security.SecurityUser;
import uz.java.backendtask.service.UserService;
import uz.java.backendtask.specification.PageRequest;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/get-me")
    public ResponseEntity<UserResponseDTO> getMe(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(service.getMe(user));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponseDTO>> search(PageRequest request, UserCriteria criteria) {
        return ResponseEntity.ok(service.search(request, criteria));
    }
}
