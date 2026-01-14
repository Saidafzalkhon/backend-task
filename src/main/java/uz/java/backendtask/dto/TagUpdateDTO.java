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
public class TagUpdateDTO  implements Serializable {

    @NotBlank(message = "code")
    @Size(min = 1, max = 255, message = "code min 1 max 255 characters")
    private String code;
}
