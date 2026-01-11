package uz.java.backendtask.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import uz.java.backendtask.dto.UserResponseDTO;
import uz.java.backendtask.entity.User;

@Mapper(componentModel = "spring",imports = {java.util.stream.Collectors.class})
public interface UserMapper {

    @Mapping(target = "userRoles", expression = "java(        user.getUserRoles().stream().map(value-> \"ROLE_\" + value.getRole().getName()).collect(Collectors.toSet()))")
    UserResponseDTO toResponse(User user);

    default Page<UserResponseDTO> toResponse(Page<User> roles) {
        return roles.map(this::toResponse);
    }
}
