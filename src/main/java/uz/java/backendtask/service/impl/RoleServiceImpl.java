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
import uz.java.backendtask.entity.Role;
import uz.java.backendtask.exception.RoleException;
import uz.java.backendtask.mapper.RoleMapper;
import uz.java.backendtask.repository.RoleRepository;
import uz.java.backendtask.service.RoleService;
import uz.java.backendtask.specification.PageRequest;
import uz.java.backendtask.specification.SpecBuilder;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;

    @Override
    @Transactional
    public RoleResponseDTO create(RoleCreateDTO createDTO) {
        checkName(createDTO.getName());
        return mapper.toResponse(repository.save(mapper.toEntity(createDTO)));
    }

    @Override
    @Transactional
    public RoleResponseDTO update(Long id, RoleUpdateDTO updateDTO) {
        Role role = findById(id);
        checkNameAndIdNot(updateDTO.getName(), id);
        return mapper.toResponse(repository.save(mapper.updateEntity(updateDTO, role)));
    }

    @Override
    @Transactional
    public RoleResponseDTO updatePatch(Long id, RoleUpdatePatchDTO updatePatchDTO) {
        Role role = findById(id);
        checkNameAndIdNot(updatePatchDTO.getName(), id);
        return mapper.toResponse(repository.save(mapper.patchEntity(updatePatchDTO, role)));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO getById(Long id) {
        return mapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list60s",
            key = "T(java.util.Objects).toString(#criteria, 'nocriteria') + ':' + T(java.util.Objects).toString(#request?.pageNumber, 0)"
                    + " + ':' + T(java.util.Objects).toString(#request?.pageSize, 10)"       )
    public Page<RoleResponseDTO> search(PageRequest request, RoleSearchCriteria criteria) {
        Specification<Role> spec = new SpecBuilder<Role>()
                .eq("id", criteria.getId())
                .like("name", criteria.getName())
                .between("createdAt", criteria.getCreatedAtFrom(), criteria.getCreatedAtTo())
                .between("updatedAt", criteria.getUpdatedAtFrom(), criteria.getUpdatedAtTo())
                .build();

        Pageable page = org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(request.getSortDirection(), request.getSortBy()));
        return mapper.toResponse(repository.findAll(spec, page));
    }

    private void checkName(String name) {
        boolean check = repository.existsByName(name);
        if (check) throw new RoleException("role already exists");
    }

    private void checkNameAndIdNot(String name, Long id) {
        boolean check = repository.existsByNameAndIdNot(name, id);
        if (check) throw new RoleException("role already exists");
    }

    @Override
    @Transactional(readOnly = true)
    public Role findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RoleException("role not found: " + id));
    }
}
