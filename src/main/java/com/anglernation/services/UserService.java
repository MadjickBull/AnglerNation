package com.anglernation.services;

import com.anglernation.dtos.CreateUserRequest;
import com.anglernation.dtos.UpdateUserRequest;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

  private UserRepo userRepo;
  private LureRepo lureRepo;
  private RodRepo rodRepo;
  private LineRepo lineRepo;

  public UserService(UserRepo userRepo, LureRepo lureRepo, RodRepo rodRepo, LineRepo lineRepo) {
    this.userRepo = userRepo;
    this.lureRepo = lureRepo;
    this.rodRepo = rodRepo;
    this.lineRepo = lineRepo;
  }

  private User getUserOrThrow(UUID userId) {
    return userRepo.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found"));
  }

  @Transactional
  public User createUser(CreateUserRequest userRequest) {

    validateUsername(userRequest.userName(), null);
    validateEmail(userRequest.email(), null);

    User user = new User();
    user.setFirstName(userRequest.firstName());
    user.setLastName(userRequest.lastName());
    user.setEmail(userRequest.email());
    user.setPhoneNumber(userRequest.phoneNumber());
    user.setPassword(userRequest.passWord());
    user.setUserName(userRequest.userName());
    userRepo.save(user);
    return user;
  }

  @Transactional
  public User updateUser(UpdateUserRequest userRequest, UUID userId) {

    validateUsername(userRequest.userName(), userId);
    validateEmail(userRequest.email(), userId);

    User user = getUserById(userId);
    Optional.ofNullable(userRequest.userName()).ifPresent(user::setUserName);
    Optional.ofNullable(userRequest.firstName()).ifPresent(user::setFirstName);
    Optional.ofNullable(userRequest.lastName()).ifPresent(user::setLastName);
    Optional.ofNullable(userRequest.email()).ifPresent(user::setEmail);
    Optional.ofNullable(userRequest.phoneNumber()).ifPresent(user::setPhoneNumber);
    Optional.ofNullable(userRequest.passWord()).ifPresent(user::setPassword);
    return userRepo.save(user);
  }

  @Transactional
  public User deleteUser(UUID userId) {
    User user = getUserById(userId);
    userRepo.delete(user);
    return user;
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

  private User getUserById(UUID userId) {
    return getUserOrThrow(userId);
  }

  private List<User> getAllUsers() {
    return userRepo.findAll();
  }

  private List<Lure> getUserLure(UUID userId) {

    return lureRepo.findAll();
  }

  private List<Rod> getUserRod(UUID userId) {
    return rodRepo.findAll();
  }

  private List<Line> getUserLines(UUID userId) {
    return lineRepo.findAll();
  }

}
