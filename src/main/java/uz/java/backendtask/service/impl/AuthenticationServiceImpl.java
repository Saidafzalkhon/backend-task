package uz.java.backendtask.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public AuthenticationAccessResponseDTO login(AuthenticationRequestDTO requestDTO) {
        User user = userService.findByUsername(requestDTO.getUsername());
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPasswordHash()))
            throw new AuthenticationException("password is invalid");
        deleteUserTokens(user);
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
    @Transactional
    public AuthenticationRefreshResponseDTO refresh(SecurityUser securityUser, HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        TokenType tokenType = jwtService.extractTokenType(authHeader.substring(7));
        if (!TokenType.REFRESH.equals(tokenType)) throw new AuthenticationException("token type not correct");
        User user = securityUser.getUser();
        deleteUserAccessTokens(user);
        String accessToken = jwtService.generateToken(new SecurityUser(user));
        saveUserToken(user, accessToken, TokenType.ACCESS);
        return AuthenticationRefreshResponseDTO.builder()
                .accessToken(accessToken)
                .expiresIn(jwtExpiration)
                .build();
    }

    @Override
    @Transactional
    public void logout(SecurityUser securityUser) {
        User user = securityUser.getUser();
        deleteUserTokens(user);
    }

    private void deleteUserTokens(User user) {
        List<Token> allValidTokenByUser = tokenRepository.findAllValidTokenByUser(user.getId());
        tokenRepository.deleteAll(allValidTokenByUser);
    }

    private void deleteUserAccessTokens(User user) {
        List<Token> allValidTokenByUser = tokenRepository.findAllValidTokenByUserByType(user.getId(), TokenType.ACCESS);
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
