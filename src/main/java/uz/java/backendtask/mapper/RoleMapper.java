package uz.java.backendtask.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.RoleCreateDTO;
import uz.java.backendtask.dto.RoleResponseDTO;
import uz.java.backendtask.dto.RoleUpdateDTO;
import uz.java.backendtask.dto.RoleUpdatePatchDTO;
import uz.java.backendtask.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toEntity(RoleCreateDTO dto);

    RoleResponseDTO toResponse(Role role);

    default Page<RoleResponseDTO> toResponse(Page<Role> roles) {
        return roles.map(this::toResponse);
    }


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    Role updateEntity(RoleUpdateDTO dto, @MappingTarget Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role patchEntity(RoleUpdatePatchDTO dto, @MappingTarget Role role);
}
