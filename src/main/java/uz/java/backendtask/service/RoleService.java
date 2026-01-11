package uz.java.backendtask.service;

import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.*;
import uz.java.backendtask.entity.Role;
import uz.java.backendtask.specification.PageRequest;

public interface RoleService {

    RoleResponseDTO create(RoleCreateDTO createDTO);

    RoleResponseDTO update(Long id, RoleUpdateDTO updateDTO);

    RoleResponseDTO updatePatch(Long id, RoleUpdatePatchDTO updatePatchDTO);

    RoleResponseDTO getById(Long id);

    Page<RoleResponseDTO> search(PageRequest request, RoleSearchCriteria criteria);

    Role findById(Long id);
}
