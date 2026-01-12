package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.Media;

@Repository
public interface MediaRepository
        extends JpaRepository<Media, Long>, JpaSpecificationExecutor<Media> {
}
