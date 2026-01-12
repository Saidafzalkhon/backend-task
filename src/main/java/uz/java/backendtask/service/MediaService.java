package uz.java.backendtask.service;

import org.springframework.web.multipart.MultipartFile;
import uz.java.backendtask.dto.MediaResponse;
import uz.java.backendtask.security.SecurityUser;

public interface MediaService {

    MediaResponse upload(
            MultipartFile file,
            Boolean isPublic,
            SecurityUser owner
    );

    void delete(Long mediaId, SecurityUser currentUserSecurity);
}
