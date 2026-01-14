package uz.java.backendtask.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.enumeration.NewsStatus;
import uz.java.backendtask.service.NewsService;
import uz.java.backendtask.specification.PageRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<NewsResponseDTO> create(@Valid
                                                  @RequestBody NewsCreateDTO dto) {
        return ResponseEntity.ok(newsService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> update(@PathVariable Long id, @Valid
    @RequestBody NewsUpdateDTO dto) {
        return ResponseEntity.ok(newsService.update(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> patch(@PathVariable Long id, @Valid
    @RequestBody NewsPatchDTO dto) {
        return ResponseEntity.ok(newsService.patch(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<NewsResponseDTO> patch(@PathVariable Long id, NewsStatus status) {
        return ResponseEntity.ok(newsService.patch(id, status));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<NewsHistoryResponseDTO>> newsHistory(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.newsHistory(id));
    }

    @DeleteMapping("/{id}/soft")
    public ResponseEntity<Void> deleteSoft(@PathVariable Long id) {
        newsService.deleteSoft(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> deleteHard(@PathVariable Long id) {
        newsService.deleteHard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<NewsResponseDTO>> search(PageRequest request, NewsSearchCriteria criteria) {
        return ResponseEntity.ok(newsService.search(request, criteria));
    }


    @DeleteMapping("/{id}/restore")
    public ResponseEntity<Void> restore(@PathVariable Long id) {
        newsService.restore(id);
        return ResponseEntity.noContent().build();
    }

}
