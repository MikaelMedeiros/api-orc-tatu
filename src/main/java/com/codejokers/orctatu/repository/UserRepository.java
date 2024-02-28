package com.codejokers.orctatu.repository;

import com.codejokers.orctatu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByGoogleId(final String googleId);
}