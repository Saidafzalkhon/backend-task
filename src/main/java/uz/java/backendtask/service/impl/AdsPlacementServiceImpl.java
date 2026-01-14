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
import uz.java.backendtask.entity.AdsPlacement;
import uz.java.backendtask.exception.AdsPlacementException;
import uz.java.backendtask.mapper.AdsPlacementMapper;
import uz.java.backendtask.repository.AdsPlacementRepository;
import uz.java.backendtask.service.AdsPlacementService;
import uz.java.backendtask.specification.PageRequest;
import uz.java.backendtask.specification.SpecBuilder;

@Service
@RequiredArgsConstructor
public class AdsPlacementServiceImpl implements AdsPlacementService {

    private final AdsPlacementRepository repository;
    private final AdsPlacementMapper mapper;

    @Override
    @Transactional
    public AdsPlacementResponseDTO create(AdsPlacementCreateDTO createDTO) {
        checkCode(createDTO.getCode());
        return mapper.toResponse(repository.save(mapper.toEntity(createDTO)));
    }

    @Override
    @Transactional
    public AdsPlacementResponseDTO update(Long id, AdsPlacementUpdateDTO updateDTO) {
        AdsPlacement placement = findById(id);
        checkCodeAndIdNot(updateDTO.getCode(), id);
        return mapper.toResponse(repository.save(mapper.updateEntity(updateDTO, placement)));
    }

    @Override
    @Transactional
    public AdsPlacementResponseDTO updatePatch(Long id, AdsPlacementUpdatePatchDTO updatePatchDTO) {
        AdsPlacement placement = findById(id);
        checkCodeAndIdNot(updatePatchDTO.getCode(), id);
        return mapper.toResponse(repository.save(mapper.patchEntity(updatePatchDTO, placement)));
    }

    @Override
    @Transactional(readOnly = true)
    public AdsPlacementResponseDTO getById(Long id) {
        return mapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list60s",
            key = "#criteria.toString() + ':' + #request.pageNumber + ':' + #request.pageSize"
    )
    public Page<AdsPlacementResponseDTO> search(PageRequest request, AdsPlacementSearchCriteria criteria) {
        Specification<AdsPlacement> spec = new SpecBuilder<AdsPlacement>()
                .eq("id", criteria.getId())
                .like("code", criteria.getCode())
                .like("title", criteria.getTitle())
                .like("description", criteria.getDescription())
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
    public AdsPlacement findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AdsPlacementException("placement not found: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        AdsPlacement placement = findById(id);
        repository.delete(placement);
    }

    private void checkCode(String name) {
        boolean check = repository.existsByCode(name);
        if (check) throw new AdsPlacementException("placement already exists");
    }

    private void checkCodeAndIdNot(String name, Long id) {
        boolean check = repository.existsByCodeAndIdNot(name, id);
        if (check) throw new AdsPlacementException("placement already exists");
    }

}
