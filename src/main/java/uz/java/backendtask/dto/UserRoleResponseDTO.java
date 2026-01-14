package uz.java.backendtask.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserRoleResponseDTO  implements Serializable {

    private UserResponseDTO user;

    private RoleResponseDTO role;
}
