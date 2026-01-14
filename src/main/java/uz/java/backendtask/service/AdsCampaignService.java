package uz.java.backendtask.service;

import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.AdsCampaign;
import uz.java.backendtask.specification.PageRequest;

public interface AdsCampaignService {

    AdsCampaignResponseDTO create(AdsCampaignCreateDTO createDTO);

    AdsCampaignResponseDTO update(Long id, AdsCampaignUpdateDTO updateDTO);

    AdsCampaignResponseDTO updatePatch(Long id, AdsCampaignUpdatePatchDTO updatePatchDTO);

    AdsCampaignResponseDTO getById(Long id);

    Page<AdsCampaignResponseDTO> search(PageRequest request, AdsCampaignSearchCriteria criteria);

    AdsCampaign findById(Long id);

    void deleteById(Long id);
}
