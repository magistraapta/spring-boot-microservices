package app.authentication.auth_service.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.authentication.auth_service.client.UserClient;
import app.authentication.auth_service.dto.AuthRequest;
import app.authentication.auth_service.dto.AuthResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

    private final UserClient userClient;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserClient userClient, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userClient = userClient;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {
        log.info("Service: Logging in user: {}", request.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return new AuthResponse(jwtService.generateToken(request.getUsername()));
        }
        throw new RuntimeException("Invalid credentials");
    }

    public AuthRequest register(AuthRequest request) {
        log.info("Service: Registering user: {}", request.getUsername());
        return userClient.createUser(request).getBody();
    }
}
