package uz.java.backendtask.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import uz.java.backendtask.dto.NewsCreateDTO;

public class PublishWindowValidator
        implements ConstraintValidator<ValidPublishWindow, NewsCreateDTO> {

    @Override
    public boolean isValid(NewsCreateDTO dto, ConstraintValidatorContext ctx) {

        if (dto.getPublishAt() == null || dto.getUnpublishAt() == null) {
            return true;
        }
        return dto.getPublishAt().isBefore(dto.getUnpublishAt());
    }
}
