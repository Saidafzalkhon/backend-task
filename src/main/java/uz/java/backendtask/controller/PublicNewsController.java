package uz.java.backendtask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.java.backendtask.dto.NewsPublicSearchCriteria;
import uz.java.backendtask.dto.NewsResponseDTO;
import uz.java.backendtask.service.NewsService;
import uz.java.backendtask.specification.PageRequest;

@RestController
@RequestMapping("/api/v1/public/news")
@RequiredArgsConstructor
public class PublicNewsController {

    private final NewsService service;


    @GetMapping
    public ResponseEntity<Page<NewsResponseDTO>> search(PageRequest request, NewsPublicSearchCriteria criteria) {
        return ResponseEntity.ok(service.searchPublic(request, criteria));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<NewsResponseDTO> getBySlugAndLang(@PathVariable String slug, String lan) {
        return ResponseEntity.ok(service.getBySlugAndLang(slug, lan));
    }
}
