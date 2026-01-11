package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRoleCreateDTO {

    @NotNull(message = "user-id is null")
    private Long userId;
    @NotNull(message = "role-id is null")
    private Long roleId;
}
