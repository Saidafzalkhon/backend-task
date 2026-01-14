package uz.java.backendtask.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.service.AdsCreativeService;
import uz.java.backendtask.specification.PageRequest;

@RestController
@RequestMapping("/v1/admin/ads-creative")
@RequiredArgsConstructor
public class AdsCreativeController {

    private final AdsCreativeService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsCreativeResponseDTO> create(@Valid @RequestBody AdsCreativeCreateDTO createDTO) {
        return ResponseEntity.ok(service.create(createDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsCreativeResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AdsCreativeUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.update(id, updateDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsCreativeResponseDTO> updatePatch(@PathVariable Long id, @Valid @RequestBody AdsCreativeUpdatePatchDTO updatePatchDTO) {
        return ResponseEntity.ok(service.updatePatch(id, updatePatchDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsCreativeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AdsCreativeResponseDTO>> search(PageRequest request, AdsCreativeSearchCriteria criteria) {
        return ResponseEntity.ok(service.search(request, criteria));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
