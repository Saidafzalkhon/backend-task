package uz.java.backendtask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.java.backendtask.dto.AdRow;
import uz.java.backendtask.service.impl.PublicAdsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/ads")
public class PublicAdsController {

    private final PublicAdsService service;

    @GetMapping("/{placementCode}")
    public ResponseEntity<AdRow> getAd(
            @PathVariable String placementCode,
            @RequestParam String lang,
            @RequestParam(required = false) Long categoryId
    ) {
        return ResponseEntity.ok(service.getAd(placementCode, lang, categoryId));
    }
}
