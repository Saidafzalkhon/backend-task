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
import uz.java.backendtask.entity.Tag;
import uz.java.backendtask.exception.TagException;
import uz.java.backendtask.mapper.TagMapper;
import uz.java.backendtask.repository.TagRepository;
import uz.java.backendtask.service.TagService;
import uz.java.backendtask.specification.PageRequest;
import uz.java.backendtask.specification.SpecBuilder;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;
    private final TagMapper mapper;

    @Override
    @Transactional
    public TagResponseDTO create(TagCreateDTO createDTO) {
        checkCode(createDTO.getCode());
        return mapper.toResponse(repository.save(mapper.toEntity(createDTO)));
    }

    @Override
    @Transactional
    public TagResponseDTO update(Long id, TagUpdateDTO updateDTO) {
        Tag tag = findById(id);
        checkCodeAndIdNot(updateDTO.getCode(), id);
        return mapper.toResponse(repository.save(mapper.updateEntity(updateDTO, tag)));
    }

    @Override
    @Transactional
    public TagResponseDTO updatePatch(Long id, TagUpdatePatchDTO updatePatchDTO) {
        Tag tag = findById(id);
        checkCodeAndIdNot(updatePatchDTO.getCode(), id);
        return mapper.toResponse(repository.save(mapper.patchEntity(updatePatchDTO, tag)));
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponseDTO getById(Long id) {
        return mapper.toResponse(findById(id));
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list120s",
            key = "#request.pageNumber + ':' + #request.pageSize"
    )
    public Page<TagResponseDTO> searchPublic(PageRequest request) {
        Pageable page = org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(request.getSortDirection(), request.getSortBy()));
        return mapper.toResponse(repository.findAll(page));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list60s",
            key = "T(java.util.Objects).toString(#criteria, 'nocriteria') + ':' + T(java.util.Objects).toString(#request?.pageNumber, 0)"
                    + " + ':' + T(java.util.Objects).toString(#request?.pageSize, 10)"        )
    public Page<TagResponseDTO> search(PageRequest request, TagSearchCriteria criteria) {
        Specification<Tag> spec = new SpecBuilder<Tag>()
                .eq("id", criteria.getId())
                .like("code", criteria.getCode())
                .between("createdAt", criteria.getCreatedAtFrom(), criteria.getCreatedAtTo())
                .between("updatedAt", criteria.getUpdatedAtFrom(), criteria.getUpdatedAtTo())
                .build();

        Pageable page = org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(request.getSortDirection(), request.getSortBy()));
        return mapper.toResponse(repository.findAll(spec, page));
    }

    private void checkCode(String name) {
        boolean check = repository.existsByCode(name);
        if (check) throw new TagException("tag already exists");
    }

    private void checkCodeAndIdNot(String name, Long id) {
        boolean check = repository.existsByCodeAndIdNot(name, id);
        if (check) throw new TagException("tag already exists");
    }

    @Override
    public Set<Tag> findAllByIds(Set<Long> tags) {
        return repository.findAllByIdIn(tags);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Tag tag = findById(id);
        repository.delete(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public Tag findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TagException("tag not found: " + id));
    }
}
