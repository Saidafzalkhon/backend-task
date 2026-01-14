package uz.java.backendtask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.java.backendtask.dto.TagResponseDTO;
import uz.java.backendtask.service.TagService;
import uz.java.backendtask.specification.PageRequest;

@RestController
@RequestMapping("/api/v1/public/tag")
@RequiredArgsConstructor
public class PublicTagController {

    private final TagService service;


    @GetMapping
    public ResponseEntity<Page<TagResponseDTO>> search(PageRequest request) {
        return ResponseEntity.ok(service.searchPublic(request));
    }
}
