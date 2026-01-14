package uz.java.backendtask.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.backendtask.dto.CategoryCreateDTO;
import uz.java.backendtask.dto.CategoryPatchDTO;
import uz.java.backendtask.dto.CategoryResponseDTO;
import uz.java.backendtask.dto.CategoryUpdateDTO;
import uz.java.backendtask.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(
            @RequestBody @Valid CategoryCreateDTO dto
    ) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid CategoryUpdateDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> patch(
            @PathVariable Long id,
            @RequestBody CategoryPatchDTO dto
    ) {
        return ResponseEntity.ok(service.patch(id, dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> get(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> list(
            @RequestParam(required = false) Long parentId,
            @RequestParam(required = false) Boolean isActive
    )  {
        return ResponseEntity.ok(service.getList(parentId, isActive));
    }
}
