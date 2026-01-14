package uz.java.backendtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.entity.AdsCampaign;

@Repository
public interface AdsCampaignRepository extends JpaRepository<AdsCampaign, Long>, JpaSpecificationExecutor<AdsCampaign> {
}
