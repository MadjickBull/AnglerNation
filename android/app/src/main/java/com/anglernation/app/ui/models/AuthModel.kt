@JsonClass(generateAdapter = true)
data class AuthResponse(
    @Json("access_token") val accessToken: String,
    @Json("token_type") val tokenType: String,
    @Json("expires_in") val expiresIn: Int
)

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json("username") @NotBlank val username: String,
    @Json("password") @NotBlank val password: String
)

@JsonClass(generateAdapter = true)
data class RegisterRequest(
    @Json("first_name") @NotBlank val firstName: String,
    @Json("last_name") @NotBlank val lastName: String,
    @Json("username") @NotBlank @Size(min = 3, max = 30) val username: String,
    @Json("email") @NotBlank @Email val email: String,
    @Json("phone_number") @NotBlank val phoneNumber: String,
    @Json("password") @NotBlank @Size(min = 8) val password: String
)