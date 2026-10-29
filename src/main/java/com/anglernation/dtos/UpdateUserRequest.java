
package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Update User Request for API v1")
public record UpdateUserRequest(
        @Schema(description = "Username") String userName,
        @Schema(description = "First name", example = "John") String firstName,
        @Schema(description = "Last name", example = "Doe") String lastName,
        @Schema(description = "Password") @Size(min = 8, message = "Password must be at least 8 characters") String passWord,
        @Schema(description = "Email address", example = "john@example.com") @Email @NotBlank String email,
        @Schema(description = "Phone number", example = "+1-555-0123") String phoneNumber
) {
}