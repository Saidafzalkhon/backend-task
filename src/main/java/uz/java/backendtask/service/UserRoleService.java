package uz.java.backendtask.service;

import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.UserRoleCreateDTO;
import uz.java.backendtask.dto.UserRoleCriteria;
import uz.java.backendtask.dto.UserRoleResponseDTO;
import uz.java.backendtask.entity.UserRole;
import uz.java.backendtask.specification.PageRequest;

public interface UserRoleService {

    UserRoleResponseDTO create(UserRoleCreateDTO createDTO);

    void delete(Long id);

    UserRoleResponseDTO getById(Long id);

    UserRole findById(Long id);

    Page<UserRoleResponseDTO> search(PageRequest request, UserRoleCriteria criteria);
}
