package uz.java.backendtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.java.backendtask.dto.MediaResponse;
import uz.java.backendtask.entity.Media;
import uz.java.backendtask.entity.User;
import uz.java.backendtask.repository.MediaRepository;
import uz.java.backendtask.security.SecurityUser;
import uz.java.backendtask.service.MediaService;
import uz.java.backendtask.service.StorageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MediaServiceImpl implements MediaService {

    private final StorageService storageService;
    private final MediaRepository mediaRepository;


    public void delete(Long mediaId, SecurityUser currentUserSecurity) {
        User currentUser = currentUserSecurity.getUser();

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        boolean isOwner =
                media.getOwner() != null &&
                        media.getOwner().getId().equals(currentUser.getId());

        boolean isAdmin =
                currentUser.getUserRoles().stream()
                        .anyMatch(r -> r.getRole().getName().equals("ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You cannot delete this media");
        }

        storageService.delete(media.getStorageKey());

        mediaRepository.delete(media);
    }

    public MediaResponse upload(
            MultipartFile file,
            Boolean isPublic,
            SecurityUser owner
    ) {
        validateFile(file);

        String extension = FileNameUtils.getExtension(file.getOriginalFilename());
        String objectKey =
                "media/"
                        + UUID.randomUUID()
                        + "." + extension;

        String url = storageService.upload(
                file,
                objectKey,
                file.getContentType()
        );

        Media media = new Media();
        media.setStorageKey(objectKey);
        media.setUrl(url);
        media.setMime(file.getContentType());
        media.setSize(file.getSize());
        media.setIsPublic(isPublic);
        media.setOwner(owner.getUser());

        // image bo‘lsa width/height o‘qiymiz
        setImageDimensionsIfNeeded(media, file);

        mediaRepository.save(media);

        return MediaResponse.from(media);
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty())
            throw new RuntimeException("File is empty");

        if (file.getSize() > 10 * 1024 * 1024)
            throw new RuntimeException("File too large (max 10MB)");
    }

    private void setImageDimensionsIfNeeded(Media media, MultipartFile file) {
        try {
            BufferedImage img =
                    ImageIO.read(file.getInputStream());
            if (img != null) {
                media.setWidth(img.getWidth());
                media.setHeight(img.getHeight());
            }
        } catch (Exception ignored) {
        }
    }
}
