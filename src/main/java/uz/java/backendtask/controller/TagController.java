package uz.java.backendtask.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.service.TagService;
import uz.java.backendtask.specification.PageRequest;

@RestController
@RequestMapping("/api/v1/admin/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagResponseDTO> create(@Valid @RequestBody TagCreateDTO createDTO) {
        return ResponseEntity.ok(service.create(createDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagResponseDTO> update(@PathVariable Long id, @Valid @RequestBody TagUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.update(id, updateDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagResponseDTO> updatePatch(@PathVariable Long id, @Valid @RequestBody TagUpdatePatchDTO updatePatchDTO) {
        return ResponseEntity.ok(service.updatePatch(id, updatePatchDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TagResponseDTO>> search(PageRequest request, TagSearchCriteria criteria) {
        return ResponseEntity.ok(service.search(request, criteria));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
