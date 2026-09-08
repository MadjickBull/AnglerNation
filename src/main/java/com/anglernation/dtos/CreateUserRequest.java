package com.anglernation.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank String userName,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Size(min = 8 , message = "Password must be at least 8 characters") String passWord,
        @NotBlank @Email String email,
        @NotBlank String phoneNumber

) {
}
