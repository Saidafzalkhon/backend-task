package uz.java.backendtask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import uz.java.backendtask.config.ValidPublishWindow;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ValidPublishWindow
public class NewsCreateDTO  implements Serializable {

    @NotNull
    private Long categoryId;

    private Long coverMediaId;

    private Boolean isFeatured = false;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime unpublishAt;

    @NotEmpty
    @Valid
    private List<NewsTranslationDTO> translations;

    private Set<Long> tagIds;
}
