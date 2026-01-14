package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class NewsTranslationDTO  implements Serializable {

    @NotBlank
    @Pattern(regexp = "^(uz|ru|en)$")
    private String lang;

    @NotBlank
    @Size(min = 3, max = 200)
    private String title;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9-]+$")
    private String slug;

    @Size(max = 5000)
    private String summary;

    @NotBlank
    private String content;

    @Size(max = 255)
    private String metaTitle;

    @Size(max = 500)
    private String metaDescription;
}
