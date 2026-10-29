package com.anglernation.services;

import com.anglernation.dtos.CreateUserRequest;
import com.anglernation.dtos.UpdateUserRequest;
import com.anglernation.dtos.UserDto;
import com.anglernation.models.lines.Line;
import com.anglernation.models.lures.Lure;
import com.anglernation.models.rods.Rod;
import com.anglernation.models.users.User;
import com.anglernation.services.repositories.LineRepo;
import com.anglernation.services.repositories.LureRepo;
import com.anglernation.services.repositories.RodRepo;
import com.anglernation.services.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final LureRepo lureRepo;
    private final RodRepo rodRepo;
    private final LineRepo lineRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, LureRepo lureRepo, RodRepo rodRepo, LineRepo lineRepo) {
        this.userRepo = userRepo;
        this.lureRepo = lureRepo;
        this.rodRepo = rodRepo;
        this.lineRepo = lineRepo;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    private User getUserOrThrow(UUID userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public UserDto createUser(CreateUserRequest userRequest) {

        validateUsername(userRequest.userName(), null);
        validateEmail(userRequest.email(), null);

        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setPassword(passwordEncoder.encode(userRequest.passWord()));
        user.setUserName(userRequest.userName());
        userRepo.save(user);
        return toDto(user);
    }

    @Transactional
    public UserDto updateUser(UpdateUserRequest userRequest, UUID userId) {

        validateUsername(userRequest.userName(), userId);
        validateEmail(userRequest.email(), userId);

        User user = getUserOrThrow(userId);
        Optional.ofNullable(userRequest.userName()).ifPresent(user::setUserName);
        Optional.ofNullable(userRequest.firstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(userRequest.lastName()).ifPresent(user::setLastName);
        Optional.ofNullable(userRequest.email()).ifPresent(user::setEmail);
        Optional.ofNullable(userRequest.phoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(userRequest.passWord()).ifPresent(newPassword -> {
            user.setPassword(passwordEncoder.encode(newPassword));
        });
        return toDto(userRepo.save(user));
    }

    @Transactional
    public UserDto deleteUser(UUID userId) {
        User user = getUserOrThrow(userId);
        userRepo.delete(user);
        return toDto(user);
    }

    public UserDto getUserById(UUID userId) {
        return toDto(getUserOrThrow(userId));
    }

    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private void validateUsername(String userName, UUID userId) {
        userRepo.findByUserName(userName).ifPresent(existing -> {
            if (!existing.getId().equals(userId)) {
                throw new EntityNotFoundException("Username already exists");
            }
        });
    }

    private void validateEmail(String email, UUID userId) {
        userRepo.findByEmail(email).ifPresent(existing -> {
            if (!existing.getId().equals(userId)) {
                throw new EntityNotFoundException("Email already exists");
            }
        });
    }

    public List<Lure> getUserLure(UUID userId) {
        return lureRepo.findByOwnerId(userId);
    }

    public List<Rod> getUserRod(UUID userId) {
        return rodRepo.findByOwnerId(userId);
    }

    public List<Line> getUserLines(UUID userId) {
        return lineRepo.findByOwnerId(userId);
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt()
        );
    }
}