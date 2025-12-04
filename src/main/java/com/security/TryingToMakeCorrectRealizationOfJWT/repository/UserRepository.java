package com.security.TryingToMakeCorrectRealizationOfJWT.repository;

import com.security.TryingToMakeCorrectRealizationOfJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
