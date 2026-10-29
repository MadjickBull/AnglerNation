package com.anglernation.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Create user request DTO for front-end API consumption")
public class CreateUserRequest {

    @Schema(
            description = "User's chosen username for login",
            example = "john_doe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String userName;

    @Schema(
            description = "User's first name",
            example = "John",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    private String firstName;

    @Schema(
            description = "User's last name",
            example = "Doe"
    )
    @NotBlank
    private String lastName;

    @Schema(
            description = "User's password",
            example = "StrongPass123!",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String passWord;

    @Schema(
            description = "User's email address",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank
    @Email
    private String email;

    @Schema(
            description = "User's phone number",
            example = "+1-555-0123-4567"
    )
    @NotBlank
    private String phoneNumber;

    public CreateUserRequest() {}

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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}