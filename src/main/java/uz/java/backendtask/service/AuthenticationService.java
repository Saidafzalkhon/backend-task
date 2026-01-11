package uz.java.backendtask.service;

import jakarta.servlet.http.HttpServletRequest;
import uz.java.backendtask.dto.AuthenticationAccessResponseDTO;
import uz.java.backendtask.dto.AuthenticationRefreshResponseDTO;
import uz.java.backendtask.dto.AuthenticationRequestDTO;
import uz.java.backendtask.security.SecurityUser;

public interface AuthenticationService {

    AuthenticationAccessResponseDTO login(AuthenticationRequestDTO requestDTO);

    AuthenticationRefreshResponseDTO refresh(SecurityUser securityUser, HttpServletRequest request);

    void logout(SecurityUser securityUse);
}
