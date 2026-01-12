package uz.java.backendtask.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.java.backendtask.dto.MediaResponse;
import uz.java.backendtask.security.SecurityUser;
import uz.java.backendtask.service.MediaService;

@RestController
@RequestMapping("/api/v1/admin/media")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','EDITOR','AUTHOR')")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaResponse> upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam(defaultValue = "false") Boolean isPublic,
            @AuthenticationPrincipal SecurityUser user
    ) {
        return ResponseEntity.ok(
                mediaService.upload(file, isPublic, user)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal SecurityUser user
    ) {
        mediaService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
