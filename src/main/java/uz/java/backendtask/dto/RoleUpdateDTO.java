package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoleUpdateDTO {
    @NotBlank(message = "name")
    @Size(min = 1, max = 255, message = "name min 1 max 255 characters")
    private String name;
}
