package uz.java.backendtask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthenticationRequestDTO {

    @NotBlank(message = "username is blank")
    @Size(min = 1, max = 255, message = "username min size 1 max size 255")
    private String username;

    @NotBlank(message = "password is blank")
    @Size(min = 1, max = 50, message = "password min size 1 max size 50")
    private String password;
}
