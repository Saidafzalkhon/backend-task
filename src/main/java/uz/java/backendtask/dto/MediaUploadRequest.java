package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MediaUploadRequest {

    @NotNull(message = "file is null")
    private MultipartFile file;

    private Boolean isPublic = false;
}
