package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoryTranslationDTO implements Serializable {

    @NotBlank(message = "lang is blank")
    private String lang;
    @NotBlank(message = "title is blank")
    private String title;
    @NotBlank(message = "slug is blank")
    private String slug;
    @NotBlank(message = "description is blank")
    private String description;
}
