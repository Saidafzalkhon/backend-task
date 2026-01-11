package uz.java.backendtask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AuthenticationRefreshResponseDTO {

    private String accessToken;

    private long expiresIn;
}
