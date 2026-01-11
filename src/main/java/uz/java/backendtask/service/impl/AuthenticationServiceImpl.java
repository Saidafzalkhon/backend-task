package uz.java.backendtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.java.backendtask.dto.AuthenticationAccessResponseDTO;
import uz.java.backendtask.dto.AuthenticationRefreshResponseDTO;
import uz.java.backendtask.dto.AuthenticationRequestDTO;
import uz.java.backendtask.entity.Token;
import uz.java.backendtask.entity.User;
import uz.java.backendtask.enumeration.TokenType;
import uz.java.backendtask.exception.AuthenticationException;
import uz.java.backendtask.repository.TokenRepository;
import uz.java.backendtask.security.JwtService;
import uz.java.backendtask.security.SecurityUser;
import uz.java.backendtask.service.AuthenticationService;
import uz.java.backendtask.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${spring.security.jwt.access.ttl}")
    private long jwtExpiration;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Override
    public AuthenticationAccessResponseDTO login(AuthenticationRequestDTO requestDTO) {
        User user = userService.findByUsername(requestDTO.getUsername());
        if (!passwordEncoder.matches(user.getPasswordHash(), requestDTO.getPassword()))
            throw new AuthenticationException("password is invalid");
        String accessToken = jwtService.generateToken(new SecurityUser(user));
        String refreshToken = jwtService.generateRefreshToken(new SecurityUser(user));
        saveUserToken(user, accessToken, TokenType.ACCESS);
        saveUserToken(user, refreshToken, TokenType.REFRESH);
        return AuthenticationAccessResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(jwtExpiration)
                .build();
    }

    @Override
    public AuthenticationRefreshResponseDTO refresh(SecurityUser securityUser, AuthenticationRefreshResponseDTO requestDTO) {
        User user = securityUser.getUser();
        String accessToken = jwtService.generateToken(new SecurityUser(user));
        String refreshToken = jwtService.generateRefreshToken(new SecurityUser(user));
        saveUserToken(user, accessToken, TokenType.ACCESS);
        saveUserToken(user, refreshToken, TokenType.REFRESH);
        return AuthenticationRefreshResponseDTO.builder()
                .accessToken(accessToken)
                .expiresIn(jwtExpiration)
                .build();
    }

    @Override
    public void logout(SecurityUser securityUser) {
        User user = securityUser.getUser();
        List<Token> allValidTokenByUser = tokenRepository.findAllValidTokenByUser(user.getId());
        tokenRepository.deleteAll(allValidTokenByUser);
    }

    private void saveUserToken(User user, String jwtToken, TokenType tokenType) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(tokenType)
                .build();
        tokenRepository.save(token);
    }
}
