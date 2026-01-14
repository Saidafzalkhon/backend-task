package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AdsCreativeTranslationRequestDTO  implements Serializable {


    @NotNull(message = "lang is null")
    private String lang;

    private String title;

    private String altText;
}
