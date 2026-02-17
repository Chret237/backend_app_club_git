package com.clubgit.backend.dto;

import com.clubgit.backend.model.Role;

public class AuthDtos {

    public record LoginRequest(String email, String password) {}

    public record AuthResponse(String token) {}

    /**
     * DTO pour la création d'un utilisateur par le Bureau.
     */
    public record CreateUserRequest(
            String firstName,
            String lastName,
            String email,
            String password,
            Role role,
            Long commissionId
    ) {}
}

