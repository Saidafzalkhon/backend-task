package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.AdsCreativeTranslation;

@Repository
public interface AdsCreativeTranslationRepository
        extends JpaRepository<AdsCreativeTranslation, Long> {
}
