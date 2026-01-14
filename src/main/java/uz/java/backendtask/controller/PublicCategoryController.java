package uz.java.backendtask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.java.backendtask.dto.CategoryResponseDTO;
import uz.java.backendtask.service.CategoryService;
import uz.java.backendtask.specification.PageRequest;

@RestController
@RequestMapping("/api/v1/public/category")
@RequiredArgsConstructor
public class PublicCategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDTO>> search(PageRequest request, String lang) {
        return ResponseEntity.ok(service.searchPublic(request, lang));
    }
}
