package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.NewsTranslation;

import java.util.Optional;

@Repository
public interface NewsTranslationRepository
        extends JpaRepository<NewsTranslation, Long> {

    boolean existsByLangAndSlug(String lang, String slug);

    boolean existsByLangAndSlugAndNewsIdNot(
            String lang,
            String slug,
            Long newsId
    );

}

