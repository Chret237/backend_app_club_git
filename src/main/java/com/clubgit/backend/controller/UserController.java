package com.clubgit.backend.controller;

import com.clubgit.backend.dto.AuthDtos.CreateUserRequest;
import com.clubgit.backend.model.Commission;
import com.clubgit.backend.model.User;
import com.clubgit.backend.repository.CommissionRepository;
import com.clubgit.backend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs (réservée au Bureau pour la création).")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserRepository userRepository;
    private final CommissionRepository commissionRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,
                          CommissionRepository commissionRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.commissionRepository = commissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Création d'un utilisateur.
     * Réservé aux membres du Bureau (règle métier).
     */
    @PostMapping
    @PreAuthorize("hasRole('BUREAU')")
    @Operation(
            summary = "Créer un utilisateur",
            description = "Création d'un membre, chef de commission ou membre du Bureau. Accessible uniquement aux membres du Bureau."
    )
    public User createUser(@RequestBody CreateUserRequest request) {
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());

        if (request.commissionId() != null) {
            Commission commission = commissionRepository.findById(request.commissionId())
                    .orElseThrow(() -> new IllegalArgumentException("Commission introuvable"));
            user.setCommission(commission);
        }

        return userRepository.save(user);
    }
}

