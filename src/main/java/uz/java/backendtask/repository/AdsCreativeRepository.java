package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.AdsCreative;
@Repository
public interface AdsCreativeRepository
        extends JpaRepository<AdsCreative, Long>, JpaSpecificationExecutor<AdsCreative> {
}
