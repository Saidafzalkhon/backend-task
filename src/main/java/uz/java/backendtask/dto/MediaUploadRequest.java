package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
public class MediaUploadRequest  implements Serializable {

    @NotNull(message = "file is null")
    private MultipartFile file;

    private Boolean isPublic = false;
}
