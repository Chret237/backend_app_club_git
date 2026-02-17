package com.clubgit.backend.repository;

import com.clubgit.backend.model.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommissionRepository extends JpaRepository<Commission, Long> {

    Optional<Commission> findByName(String name);
}

