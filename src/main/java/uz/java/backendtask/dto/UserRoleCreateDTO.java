package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserRoleCreateDTO  implements Serializable {

    @NotNull(message = "user-id is null")
    private Long userId;
    @NotNull(message = "role-id is null")
    private Long roleId;
}
