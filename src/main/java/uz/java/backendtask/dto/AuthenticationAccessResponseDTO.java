package uz.java.backendtask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AuthenticationAccessResponseDTO {

    private String accessToken;

    private String refreshToken;

    private long expiresIn;
}
