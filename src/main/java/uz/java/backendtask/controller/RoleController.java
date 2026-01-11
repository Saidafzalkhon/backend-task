package uz.java.backendtask.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.service.RoleService;
import uz.java.backendtask.specification.PageRequest;

@RestController
@RequestMapping("/api/v1/admin/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponseDTO> create(@Valid @RequestBody RoleCreateDTO createDTO) {
        return ResponseEntity.ok(service.create(createDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RoleUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.update(id, updateDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponseDTO> updatePatch(@PathVariable Long id, @Valid @RequestBody RoleUpdatePatchDTO updatePatchDTO) {
        return ResponseEntity.ok(service.updatePatch(id, updatePatchDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<RoleResponseDTO>> search(PageRequest request, RoleSearchCriteria criteria) {
        return ResponseEntity.ok(service.search(request, criteria));
    }
}
