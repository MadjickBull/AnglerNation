package com.anglernation.app.ui.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.dataStore.core.DataStore
import androidx.dataStore.core Preferences
import androidx.dataStore.core Serializer
import com.anglernation.app.ui.models.AuthResponse
import kotlinx.serialization.JSON
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.IOException
import java.util.concurrent.Executor

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

    // Login with JWT
    suspend fun login(username: String, password: String): AuthToken {
        // TODO: Call backend auth endpoint
        // For now, create a mock token
        val expiryTime = System.currentTimeMillis() + (15 * 60 * 1000) // 15 minutes
        val token = AuthToken(
            accessToken = "mock_jwt_token_$username",
            tokenType = "Bearer",
            expiresIn = 900,
            expiryTime = expiryTime
        )
        saveToken(token)
        return token
    }

    // Register with JWT
    suspend fun register(firstName: String, lastName: String, username: String, email: String, phoneNumber: String, password: String): AuthToken {
        // TODO: Call backend register endpoint
        val expiryTime = System.currentTimeMillis() + (15 * 60 * 1000) // 15 minutes
        val token = AuthToken(
            accessToken = "mock_jwt_register_$username",
            tokenType = "Bearer",
            expiresIn = 900,
            expiryTime = expiryTime
        )
        saveToken(token)
        return token
    }

    // Get username from token
    fun getUsername(): String? {
        val token = getToken()
        // Extract username from JWT token (mock implementation)
        if (token.accessToken.startsWith("mock_jwt_")) {
            return token.accessToken.removePrefix("mock_jwt_")
        }
        return null
    }
}