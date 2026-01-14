package uz.java.backendtask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class AuthenticationAccessResponseDTO  implements Serializable {

    private String accessToken;

    private String refreshToken;

    private long expiresIn;
}
