package uz.java.backendtask.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.java.backendtask.dto.NewsTranslationDTO;
import uz.java.backendtask.exception.ConflictException;
import uz.java.backendtask.exception.ValidationException;
import uz.java.backendtask.repository.NewsTranslationRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SlugValidator {

    private final NewsTranslationRepository repository;

    public void validateCreate(List<NewsTranslationDTO> list) {

        Set<String> langs = new HashSet<>();
        for (var t : list) {
            if (!langs.add(t.getLang())) {
                throw new ValidationException(
                        "Duplicate lang: " + t.getLang()
                );
            }
            if (repository.existsByLangAndSlug(t.getLang(), t.getSlug())) {
                throw new ConflictException(
                        "Slug already exists: " + t.getSlug()
                );
            }
        }
    }

    public void validateUpdate(Long newsId, List<NewsTranslationDTO> list) {
        for (var t : list) {
            if (repository.existsByLangAndSlugAndNewsIdNot(
                    t.getLang(),
                    t.getSlug(),
                    newsId
            )) {
                throw new ConflictException(
                        "Slug already exists: " + t.getSlug()
                );
            }
        }
    }
}

