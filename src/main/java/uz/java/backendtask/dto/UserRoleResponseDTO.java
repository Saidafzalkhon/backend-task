package uz.java.backendtask.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRoleResponseDTO {

    private UserResponseDTO user;

    private RoleResponseDTO role;
}
