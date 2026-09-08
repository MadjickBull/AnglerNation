package com.anglernation.services.repositories;

import com.anglernation.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User , UUID> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String username);

}
