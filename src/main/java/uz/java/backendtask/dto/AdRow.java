package uz.java.backendtask.dto;

import java.io.Serializable;

public record AdRow(
        Long creativeId,
        String creativeType,
        String title,
        String altText,
        String landingUrl,
        Long imageMediaId,
        String htmlSnippet,
        Integer weight
) implements Serializable{}
