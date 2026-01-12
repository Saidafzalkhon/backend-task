package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.AdsAssignment;

@Repository
public interface AdsAssignmentRepository
        extends JpaRepository<AdsAssignment, Long>,
        JpaSpecificationExecutor<AdsAssignment> {
}
