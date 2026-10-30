package com.anglernation.app.ui

import com.anglernation.app.ui.auth.AuthRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {

    override fun intercept(chain: okhttp3.Chain): Response {
        val request = chain.request()
        val authToken = AuthRepository.getToken()

        // Add Authorization header if user is authenticated
        val authHeader = if (authToken.accessToken.isNotEmpty()) {
            request.newBuilder()
                .header("Authorization", "Bearer ${authToken.accessToken}")
                .build()
        } else {
            request
        }

        try {
            return chain.proceed(authHeader)
        } catch (e: IOException) {
            throw IOException("Auth interceptor error: ${e.message}", e)
        }
    }
}