package uz.java.backendtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.java.backendtask.dto.UserCriteria;
import uz.java.backendtask.dto.UserResponseDTO;
import uz.java.backendtask.entity.User;
import uz.java.backendtask.exception.UnauthorizedException;
import uz.java.backendtask.exception.UserException;
import uz.java.backendtask.mapper.UserMapper;
import uz.java.backendtask.repository.UserRepository;
import uz.java.backendtask.security.SecurityUser;
import uz.java.backendtask.service.UserService;
import uz.java.backendtask.specification.PageRequest;
import uz.java.backendtask.specification.SpecBuilder;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new UserException("user not found: " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getById(Long id) {
        return mapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getMe(SecurityUser user) {
        return mapper.toResponse(user.getUser());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = "list60s",
            key = "#criteria.toString() + ':' + #request.pageNumber + ':' + #request.pageSize"
    )
    public Page<UserResponseDTO> search(PageRequest request, UserCriteria criteria) {
        Specification<User> spec = new SpecBuilder<User>()
                .eq("id", criteria.getId())
                .like("username", criteria.getUsername())
                .like("email", criteria.getEmail())
                .like("fullName", criteria.getFullName())
                .eq("active", criteria.getActive())
                .between("createdAt", criteria.getCreatedAtFrom(), criteria.getCreatedAtTo())
                .between("updatedAt", criteria.getUpdatedAtFrom(), criteria.getUpdatedAtTo())
                .build();

        Pageable page = org.springframework.data.domain.PageRequest.of(request.getPageNumber(), request.getPageSize(),
                Sort.by(request.getSortDirection(), request.getSortBy()));
        return mapper.toResponse(repository.findAll(spec, page));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserException("user not found: " + id));
    }

    public User current() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth.getPrincipal().equals("anonymousUser")) {
            throw new UnauthorizedException("Unauthorized");
        }
        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        return securityUser.getUser();
    }
}
