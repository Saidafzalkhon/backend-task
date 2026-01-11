package uz.java.backendtask.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.backendtask.dto.UserRoleCreateDTO;
import uz.java.backendtask.dto.UserRoleCriteria;
import uz.java.backendtask.dto.UserRoleResponseDTO;
import uz.java.backendtask.service.UserRoleService;
import uz.java.backendtask.specification.PageRequest;

@RestController
@RequestMapping("/api/v1/user-role")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserRoleResponseDTO> create(@Valid @RequestBody UserRoleCreateDTO createDTO) {
        return ResponseEntity.ok(service.create(createDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserRoleResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserRoleResponseDTO>> search(PageRequest request, UserRoleCriteria criteria) {
        return ResponseEntity.ok(service.search(request, criteria));
    }
}
