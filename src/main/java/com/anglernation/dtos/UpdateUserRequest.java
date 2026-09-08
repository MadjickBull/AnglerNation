package com.anglernation.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank String userName,
        String firstName,
        String lastName,
        @Size(min = 8 , message = "Password must be at least 8 characters") String passWord,
        @Email @NotBlank String email,
        String phoneNumber
) {
}
