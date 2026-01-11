package uz.java.backendtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.backendtask.dto.UserRoleCreateDTO;
import uz.java.backendtask.dto.UserRoleCriteria;
import uz.java.backendtask.dto.UserRoleResponseDTO;
import uz.java.backendtask.entity.UserRole;
import uz.java.backendtask.exception.UserRoleException;
import uz.java.backendtask.mapper.UserRoleMapper;
import uz.java.backendtask.repository.UserRoleRepository;
import uz.java.backendtask.service.RoleService;
import uz.java.backendtask.service.UserRoleService;
import uz.java.backendtask.service.UserService;
import uz.java.backendtask.specification.PageRequest;
import uz.java.backendtask.specification.SpecBuilder;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository repository;
    private final UserRoleMapper mapper;
    private final UserService userService;
    private final RoleService roleService;

    @Override
    @Transactional
    public UserRoleResponseDTO create(UserRoleCreateDTO createDTO) {
        checkRoleAndUserExists(createDTO.getUserId(), createDTO.getRoleId());
        UserRole userRole = UserRole.builder()
                .user(userService.findById(createDTO.getUserId()))
                .role(roleService.findById(createDTO.getRoleId()))
                .build();
        return mapper.toResponse(repository.save(userRole));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        UserRole userRole = findById(id);
        repository.delete(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public UserRoleResponseDTO getById(Long id) {
        return mapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserRoleResponseDTO> search(PageRequest request, UserRoleCriteria criteria) {
        Specification<UserRole> spec = new SpecBuilder<UserRole>()
                .eq("id", criteria.getId())
                .joinEq("user", "id", criteria.getUserId())
                .joinLike("user", "username", criteria.getUsername())
                .joinLike("user", "email", criteria.getEmail())
                .joinLike("user", "fullName", criteria.getFullName())
                .joinEq("user", "active", criteria.getActive())
                .joinEq("role", "id", criteria.getRoleId())
                .joinLike("role", "name", criteria.getRoleName())
                .between("createdAt", criteria.getCreatedAtFrom(), criteria.getCreatedAtTo())
                .between("updatedAt", criteria.getUpdatedAtFrom(), criteria.getUpdatedAtTo())
                .build();

        Pageable page = org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(request.getSortDirection(), request.getSortBy()));
        return mapper.toResponse(repository.findAll(spec, page));
    }

    private void checkRoleAndUserExists(Long userId, Long roleId) {
        boolean b = repository.existsByUserIdAndRoleId(userId, roleId);
        if (b) throw new UserRoleException("user-role already exists: user-id: " + userId + " role-id: " + roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserRole findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserRoleException("user-role not found: " + id));
    }
}
