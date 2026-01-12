package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.CategoryTranslation;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryTranslationRepository
        extends JpaRepository<CategoryTranslation, Long>, JpaSpecificationExecutor<CategoryTranslation> {

    Optional<CategoryTranslation> findBySlugAndLang(String slug, String lang);

    List<CategoryTranslation> findByLang(String lang);
}
