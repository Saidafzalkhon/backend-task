package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class RoleUpdatePatchDTO  implements Serializable {

    @NotBlank(message = "name")
    @Size(min = 1, max = 255, message = "name min 1 max 255 characters")
    private String name;
}
