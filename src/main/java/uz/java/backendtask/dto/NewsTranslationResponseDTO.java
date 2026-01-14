package uz.java.backendtask.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class NewsTranslationResponseDTO implements Serializable {

    private String lang;
    private String title;
    private String slug;
    private String summary;
    private String content;
    private String metaTitle;
    private String metaDescription;
}
