package uz.java.backendtask.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.UserRoleResponseDTO;
import uz.java.backendtask.entity.UserRole;

@Mapper(componentModel = "spring")
public abstract class UserRoleMapper {

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected RoleMapper roleMapper;

    @Mapping(target = "user", expression = "java(userMapper.toResponse(userRole.getUser()))")
    @Mapping(target = "role", expression = "java(roleMapper.toResponse(userRole.getRole()))")
    public abstract UserRoleResponseDTO toResponse(UserRole userRole);

    public Page<UserRoleResponseDTO> toResponse(Page<UserRole> roles) {
        return roles.map(this::toResponse);
    }
}
