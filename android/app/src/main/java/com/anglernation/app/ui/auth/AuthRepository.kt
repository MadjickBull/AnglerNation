package com.anglernation.app.ui.auth

import android.content.Context
import androidx.dataStore.core.DataStore
import androidx.dataStore.core Preferences
import androidx.dataStore.core Serializer
import com.anglernation.app.ui.models.AuthResponse
import com.anglernation.app.ui.ui.AnglerNationApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.timeout
import java.util.concurrent.TimeUnit

// Auth data class for serialization
@Serializable
data class AuthToken(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val expiryTime: Long // milliseconds since epoch
)

// Serializer for AuthToken using Moshi
val authTokenSerializer: Serializer<AuthToken> = object : Serializer<AuthToken> {
    private val json = Json { decodeUnknownFields = true }

    override val deserialize: (bytes: ByteArray) -> AuthToken {
        { byteArray ->
            val map: Map<String, Any> = json.decodeFromString<Map<String, Any>>(String(byteArray))
            AuthToken(
                accessToken = map["accessToken"] as String,
                tokenType = map["tokenType"] as String,
                expiresIn = map["expiresIn"] as Int,
                expiryTime = map["expiryTime"] as Long
            )
        }
    }

    override val serialize: (value: AuthToken) -> ByteArray {
        { authToken ->
            json.encodeToString(
                AuthData(authToken.accessToken, authToken.tokenType, authToken.expiresIn, authToken.expiryTime)
            )
        }
    }
}

// Data storage for auth token
class AuthData(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val expiryTime: Long
) : Serializable {

    companion object {
        const val FILE_NAME = "auth.datastore"
    }
}

object AuthRepository {

    const val TOKEN_KEY = "auth_token"

    private val dataStore: DataStore<AuthToken> = dataStore(
        name = AuthData.FILE_NAME,
        defaultValue = AuthToken(
            accessToken = "",
            tokenType = "",
            expiresIn = 0,
            expiryTime = 0L
        ),
        serializer = authTokenSerializer
    )

    // Save auth token to DataStore
    suspend fun saveToken(token: AuthToken) {
        dataStore.write { it = token }
    }

    // Get auth token from DataStore
    suspend fun getToken(): AuthToken {
        return dataStore.read { it ?: AuthToken(accessToken = "", tokenType = "", expiresIn = 0, expiryTime = 0L) }
    }

    // Clear auth token (logout)
    suspend fun clearToken() {
        dataStore.write { it = AuthToken(accessToken = "", tokenType = "", expiresIn = 0, expiryTime = 0L) }
    }

    // Check if user is authenticated
    suspend fun isAuthenticated(): Boolean {
        val token = getToken()
        return token.accessToken.isNotEmpty() && System.currentTimeMillis() < token.expiryTime
    }

    // Login with JWT - calls actual backend endpoint
    suspend fun login(username: String, password: String): AuthToken {
        runBlocking {
            timeout(10, TimeUnit.SECONDS) {
                try {
                    val api = AnglerNationApi.create()
                    val response = api.login(
                        com.anglernation.app.ui.models.LoginRequest(username = username, password = password)
                    )
                    val expiryTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(response.expiresIn.toLong())
                    val token = AuthToken(
                        accessToken = response.accessToken,
                        tokenType = response.tokenType,
                        expiresIn = response.expiresIn,
                        expiryTime = expiryTime
                    )
                    saveToken(token)
                    return token
                } catch (e: Exception) {
                    throw RuntimeException("Login failed: ${e.message}")
                }
            }
        }
    }

    // Register with JWT - calls actual backend endpoint
    suspend fun register(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        phoneNumber: String,
        password: String
    ): AuthToken {
        runBlocking {
            timeout(10, TimeUnit.SECONDS) {
                try {
                    val api = AnglerNationApi.create()
                    val response = api.register(
                        com.anglernation.app.ui.models.RegisterRequest(
                            firstName = firstName,
                            lastName = lastName,
                            username = username,
                            email = email,
                            phoneNumber = phoneNumber,
                            password = password
                        )
                    )
                    val expiryTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(response.expiresIn.toLong())
                    val token = AuthToken(
                        accessToken = response.accessToken,
                        tokenType = response.tokenType,
                        expiresIn = response.expiresIn,
                        expiryTime = expiryTime
                    )
                    saveToken(token)
                    return token
                } catch (e: Exception) {
                    throw RuntimeException("Registration failed: ${e.message}")
                }
            }
        }
    }

    // Get username from token
    fun getUsername(): String? {
        val token = getToken()
        // Extract username from JWT token
        if (token.accessToken.isNotEmpty()) {
            // In a real app, you'd decode the JWT and extract the subject
            // For now, return the token itself or a parsed identity
            return token.accessToken
        }
        return null
    }
}