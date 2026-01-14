package uz.java.backendtask.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.service.AdsCampaignService;
import uz.java.backendtask.specification.PageRequest;

@RestController
@RequestMapping("/api/v1/admin/ads/campaign")
@RequiredArgsConstructor
public class AdsCampaignController {

    private final AdsCampaignService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsCampaignResponseDTO> create(@Valid @RequestBody AdsCampaignCreateDTO createDTO) {
        return ResponseEntity.ok(service.create(createDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsCampaignResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AdsCampaignUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.update(id, updateDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsCampaignResponseDTO> updatePatch(@PathVariable Long id, @Valid @RequestBody AdsCampaignUpdatePatchDTO updatePatchDTO) {
        return ResponseEntity.ok(service.updatePatch(id, updatePatchDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdsCampaignResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AdsCampaignResponseDTO>> search(PageRequest request, AdsCampaignSearchCriteria criteria) {
        return ResponseEntity.ok(service.search(request, criteria));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
