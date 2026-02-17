package com.clubgit.backend.controller;

import com.clubgit.backend.dto.AuthDtos.AuthResponse;
import com.clubgit.backend.dto.AuthDtos.LoginRequest;
import com.clubgit.backend.model.Role;
import com.clubgit.backend.model.User;
import com.clubgit.backend.repository.UserRepository;
import com.clubgit.backend.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "Endpoints d'authentification et de bootstrap du Bureau")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Authentifie un utilisateur et retourne un token JWT.")
    public AuthResponse login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        String token = jwtService.generateToken((org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal());
        return new AuthResponse(token);
    }

    /**
     * Endpoint de bootstrap pour créer le tout premier utilisateur du Bureau.
     * Accessible seulement tant qu'aucun utilisateur avec le rôle BUREAU n'existe.
     */
    @PostMapping("/bootstrap-bureau")
    @Operation(
            summary = "Bootstrap du Bureau",
            description = "Crée le tout premier utilisateur du Bureau. Ne peut être appelé qu'une seule fois, avant la création d'un rôle BUREAU."
    )
    public AuthResponse bootstrapBureau(@RequestBody LoginRequest request) {
        boolean existsBureau = userRepository.existsByRole(Role.BUREAU);
        if (existsBureau) {
            throw new RuntimeException("Un utilisateur du Bureau existe déjà.");
        }

        User user = new User();
        user.setFirstName("Bureau");
        user.setLastName("Admin");
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.BUREAU);

        userRepository.save(user);

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        String token = jwtService.generateToken((org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal());
        return new AuthResponse(token);
    }
}

