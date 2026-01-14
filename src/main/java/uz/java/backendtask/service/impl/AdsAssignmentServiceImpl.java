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
import uz.java.backendtask.entity.AdsAssignment;
import uz.java.backendtask.exception.AssigmentException;
import uz.java.backendtask.mapper.AdsAssignmentMapper;
import uz.java.backendtask.repository.AdsAssignmentRepository;
import uz.java.backendtask.service.AdsAssignmentService;
import uz.java.backendtask.service.AdsCampaignService;
import uz.java.backendtask.service.AdsCreativeService;
import uz.java.backendtask.service.AdsPlacementService;
import uz.java.backendtask.specification.PageRequest;
import uz.java.backendtask.specification.SpecBuilder;

@Service
@RequiredArgsConstructor
public class AdsAssignmentServiceImpl implements AdsAssignmentService {

    private final AdsAssignmentRepository repository;
    private final AdsAssignmentMapper mapper;
    private final AdsCampaignService campaignService;
    private final AdsCreativeService adsCreativeService;
    private final AdsPlacementService adsPlacementService;

    @Override
    @Transactional
    public AdsAssignmentResponseDTO create(AdsAssignmentCreateDTO createDTO) {
        AdsAssignment assignment = mapper.toEntity(createDTO);
        assignment.setCampaign(campaignService.findById(createDTO.getCampaignId()));
        assignment.setCreative(adsCreativeService.findById(createDTO.getCreativeId()));
        assignment.setPlacement(adsPlacementService.findById(createDTO.getPlacementId()));
        return mapper.toResponse(repository.save(assignment));
    }

    @Override
    @Transactional
    public AdsAssignmentResponseDTO update(Long id, AdsAssignmentUpdateDTO updateDTO) {
        AdsAssignment assignment = findById(id);
        assignment = mapper.updateEntity(updateDTO, assignment);
        assignment.setCampaign(campaignService.findById(updateDTO.getCampaignId()));
        assignment.setCreative(adsCreativeService.findById(updateDTO.getCreativeId()));
        assignment.setPlacement(adsPlacementService.findById(updateDTO.getPlacementId()));
        return mapper.toResponse(repository.save(assignment));
    }

    @Override
    @Transactional
    public AdsAssignmentResponseDTO updatePatch(Long id, AdsAssignmentUpdatePatchDTO updatePatchDTO) {
        AdsAssignment assignment = findById(id);
        assignment = mapper.patchEntity(updatePatchDTO, assignment);
        assignment.setCampaign(updatePatchDTO.getCampaignId() != null ? campaignService.findById(updatePatchDTO.getCampaignId()) : assignment.getCampaign());
        assignment.setCreative(updatePatchDTO.getCreativeId() != null ? adsCreativeService.findById(updatePatchDTO.getCreativeId()) : assignment.getCreative());
        assignment.setPlacement(updatePatchDTO.getPlacementId() != null ? adsPlacementService.findById(updatePatchDTO.getPlacementId()) : assignment.getPlacement());
        return mapper.toResponse(repository.save(mapper.patchEntity(updatePatchDTO, assignment)));
    }

    @Override
    @Transactional(readOnly = true)
    public AdsAssignmentResponseDTO getById(Long id) {
        return mapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)

    @Cacheable(
            value = "list60s",
            key = "T(java.util.Objects).toString(#criteria, 'nocriteria') + ':' + T(java.util.Objects).toString(#request?.pageNumber, 0)"
                    + " + ':' + T(java.util.Objects).toString(#request?.pageSize, 10)"    )
    public Page<AdsAssignmentResponseDTO> search(PageRequest request, AdsAssignmentSearchCriteria criteria) {
        Specification<AdsAssignment> spec = new SpecBuilder<AdsAssignment>()
                .eq("id", criteria.getId())
                .joinEq("placement", "id", criteria.getPlacementId())
                .joinEq("campaign", "id", criteria.getCampaignId())
                .joinEq("creative", "id", criteria.getCreativeId())
                .eq("weight", criteria.getWeight())
                .eq("isActive", criteria.getIsActive())
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
    public AdsAssignment findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AssigmentException("assigment not found"));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        AdsAssignment assignment = findById(id);
        repository.delete(assignment);
    }
}
