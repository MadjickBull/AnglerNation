
package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Create User Request for API v1")
public record CreateUserRequest(
        @Schema(description = "Username", example = "john_doe") @NotBlank String userName,
        @Schema(description = "First name", example = "John") @NotBlank String firstName,
        @Schema(description = "Last name", example = "Doe") @NotBlank String lastName,
        @Schema(description = "Password", example = "StrongPass123") @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String passWord,
        @Schema(description = "Email address", example = "john@example.com") @NotBlank @Email String email,
        @Schema(description = "Phone number", example = "+1-555-0123") String phoneNumber
) {
}