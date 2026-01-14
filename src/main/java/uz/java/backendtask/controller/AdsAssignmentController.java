package uz.java.backendtask.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.service.AdsAssignmentService;
import uz.java.backendtask.specification.PageRequest;

@RestController
@RequestMapping("/api/v1/admin/ads/assignment")
@RequiredArgsConstructor
public class AdsAssignmentController {

    private final AdsAssignmentService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsAssignmentResponseDTO> create(@Valid @RequestBody AdsAssignmentCreateDTO createDTO) {
        return ResponseEntity.ok(service.create(createDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsAssignmentResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AdsAssignmentUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.update(id, updateDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsAssignmentResponseDTO> updatePatch(@PathVariable Long id, @Valid @RequestBody AdsAssignmentUpdatePatchDTO updatePatchDTO) {
        return ResponseEntity.ok(service.updatePatch(id, updatePatchDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsAssignmentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AdsAssignmentResponseDTO>> search(PageRequest request, AdsAssignmentSearchCriteria criteria) {
        return ResponseEntity.ok(service.search(request, criteria));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
