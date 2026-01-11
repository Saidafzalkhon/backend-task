package uz.java.backendtask.service;

import uz.java.backendtask.dto.AuthenticationAccessResponseDTO;
import uz.java.backendtask.dto.AuthenticationRefreshResponseDTO;
import uz.java.backendtask.dto.AuthenticationRequestDTO;
import uz.java.backendtask.security.SecurityUser;

public interface AuthenticationService {

    AuthenticationAccessResponseDTO login(AuthenticationRequestDTO requestDTO);

    AuthenticationRefreshResponseDTO refresh(SecurityUser securityUser, AuthenticationRefreshResponseDTO requestDTO);

    void logout(SecurityUser securityUse);
}
