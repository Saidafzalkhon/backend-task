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
import uz.java.backendtask.entity.AdsCreative;
import uz.java.backendtask.exception.AdsCreativeException;
import uz.java.backendtask.mapper.AdsCreativeMapper;
import uz.java.backendtask.repository.AdsCreativeRepository;
import uz.java.backendtask.service.AdsCampaignService;
import uz.java.backendtask.service.AdsCreativeService;
import uz.java.backendtask.service.MediaService;
import uz.java.backendtask.specification.PageRequest;
import uz.java.backendtask.specification.SpecBuilder;

@Service
@RequiredArgsConstructor
public class AdsCreativeServiceImpl implements AdsCreativeService {
    private final AdsCreativeRepository repository;
    private final AdsCreativeMapper mapper;
    private final AdsCampaignService campaignService;
    private final MediaService mediaService;

    @Override
    @Transactional
    public AdsCreativeResponseDTO create(AdsCreativeCreateDTO createDTO) {
        AdsCreative creative = mapper.toEntity(createDTO);
        creative.setCampaign(campaignService.findById(createDTO.getCampaignId()));
        creative.setImageMedia(createDTO.getMediaId() != null ? mediaService.findById(createDTO.getMediaId()) : null);
        if (creative.getTranslations() != null) {
            creative.getTranslations().forEach(t ->
                    t.setCreative(creative)
            );
        }
        return mapper.toResponse(repository.save(creative));
    }

    @Override
    @Transactional
    public AdsCreativeResponseDTO update(Long id, AdsCreativeUpdateDTO updateDTO) {
        AdsCreative creative = findById(id);
        creative.setCampaign(campaignService.findById(updateDTO.getCampaignId()));
        creative.setImageMedia(updateDTO.getMediaId() != null ? mediaService.findById(updateDTO.getMediaId()) : null);
        return mapper.toResponse(mapper.updateEntity(updateDTO, creative));
    }

    @Override
    @Transactional
    public AdsCreativeResponseDTO updatePatch(Long id, AdsCreativeUpdatePatchDTO updatePatchDTO) {
        AdsCreative creative = findById(id);
        creative.setCampaign(updatePatchDTO.getMediaId() != null ? campaignService.findById(updatePatchDTO.getCampaignId()) : creative.getCampaign());
        creative.setImageMedia(updatePatchDTO.getMediaId() != null ? mediaService.findById(updatePatchDTO.getMediaId()) : creative.getImageMedia());
        return mapper.toResponse(mapper.patchEntity(updatePatchDTO, creative));
    }

    @Override
    @Transactional(readOnly = true)
    public AdsCreativeResponseDTO getById(Long id) {
        return mapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list60s",
            key = "#criteria.toString() + ':' + #request.pageNumber + ':' + #request.pageSize"
    )
    public Page<AdsCreativeResponseDTO> search(PageRequest request, AdsCreativeSearchCriteria criteria) {
        Specification<AdsCreative> spec = new SpecBuilder<AdsCreative>()
                .eq("id", criteria.getId())
                .joinEq("campaign","id", criteria.getCampaignId())
                .eq("type", criteria.getType())
                .like("landingUrl", criteria.getLandingUrl())
                .joinEq("imageMedia","id", criteria.getImageMediaId())
                .like("htmlSnippet", criteria.getHtmlSnippet())
                .eq("isActive", criteria.getIsActive())
                .between("createdAt", criteria.getCreatedAtFrom(), criteria.getCreatedAtTo())
                .between("updatedAt", criteria.getUpdatedAtFrom(), criteria.getUpdatedAtTo())
                .build();

        Pageable page = org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(request.getSortDirection(), request.getSortBy()));
        return mapper.toResponse(repository.findAll(spec, page));
    }

    @Override
    @Transactional(readOnly = true)
    public AdsCreative findById(Long id) {
        return repository.findById(id).orElseThrow(()-> new AdsCreativeException("ads-creative not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteById(Long id) {
        AdsCreative creative = findById(id);
        repository.delete(creative);
    }
}
