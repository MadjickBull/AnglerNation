package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "User DTO with full front-end payload")
public class UserDto {

    @Schema(
            description = "Unique identifier for the user",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID id;

    @Schema(
            description = "User's chosen username for login",
            example = "john_doe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String userName;

    @Schema(
            description = "User's first name",
            example = "John",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String firstName;

    @Schema(
            description = "User's last name",
            example = "Doe"
    )
    private String lastName;

    @Schema(
            description = "User's email address",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Schema(
            description = "Account creation timestamp",
            example = "2024-01-15T10:30:00Z"
    )
    private Instant createdAt;

    public UserDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}