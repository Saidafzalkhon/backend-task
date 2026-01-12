package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TagCreateDTO {

    @NotBlank(message = "code")
    @Size(min = 1,max = 255, message = "code min 1 max 255 characters")
    private String code;
}
