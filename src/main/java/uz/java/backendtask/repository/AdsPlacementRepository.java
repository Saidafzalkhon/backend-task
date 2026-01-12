package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.AdsPlacement;

@Repository
public interface AdsPlacementRepository
        extends JpaRepository<AdsPlacement, Long> {
}
