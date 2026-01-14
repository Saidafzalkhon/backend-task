package uz.java.backendtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.AdsCampaign;
import uz.java.backendtask.exception.AdsCampaignException;
import uz.java.backendtask.mapper.AdsCampaignMapper;
import uz.java.backendtask.repository.AdsCampaignRepository;
import uz.java.backendtask.service.AdsCampaignService;
import uz.java.backendtask.specification.PageRequest;
import uz.java.backendtask.specification.SpecBuilder;

@Service
@RequiredArgsConstructor
public class AdsCampaignServiceImpl implements AdsCampaignService {

    private final AdsCampaignRepository repository;

    private final AdsCampaignMapper mapper;

    @Override
    @Transactional
    public AdsCampaignResponseDTO create(AdsCampaignCreateDTO createDTO) {
        return mapper.toResponse(repository.save(mapper.toEntity(createDTO)));
    }

    @Override
    @Transactional
    public AdsCampaignResponseDTO update(Long id, AdsCampaignUpdateDTO updateDTO) {
        AdsCampaign campaign = findById(id);
        return mapper.toResponse(repository.save(mapper.updateEntity(updateDTO, campaign)));
    }

    @Override
    @Transactional
    public AdsCampaignResponseDTO updatePatch(Long id, AdsCampaignUpdatePatchDTO updatePatchDTO) {
        AdsCampaign campaign = findById(id);
        return mapper.toResponse(repository.save(mapper.patchEntity(updatePatchDTO, campaign)));
    }

    @Override
    @Transactional(readOnly = true)
    public AdsCampaignResponseDTO getById(Long id) {
        return mapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list60s",
            key = "#criteria.toString() + ':' + #request.pageNumber + ':' + #request.pageSize"
    )
    public Page<AdsCampaignResponseDTO> search(PageRequest request, AdsCampaignSearchCriteria criteria) {
        Specification<AdsCampaign> spec = new SpecBuilder<AdsCampaign>()
                .eq("id", criteria.getId())
                .like("name", criteria.getName())
                .like("advertiser", criteria.getAdvertiser())
                .eq("status", criteria.getStatus())
                .eq("dailyCapImpressions", criteria.getDailyCapImpressions())
                .eq("dailyCapClicks", criteria.getDailyCapClicks())
                .between("startAt", criteria.getStartAtFrom(), criteria.getStartAtTo())
                .between("endAt", criteria.getEndAtFrom(), criteria.getEndAtTo())
                .between("createdAt", criteria.getCreatedAtFrom(), criteria.getCreatedAtTo())
                .between("updatedAt", criteria.getUpdatedAtFrom(), criteria.getUpdatedAtTo())
                .build();

        Pageable page = org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(request.getSortDirection(), request.getSortBy()));
        return mapper.toResponse(repository.findAll(spec, page));
    }

    @Override
    @Transactional(readOnly = true)
    public AdsCampaign findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AdsCampaignException("campaign not found: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        AdsCampaign campaign = findById(id);
        repository.delete(campaign);
    }
}
