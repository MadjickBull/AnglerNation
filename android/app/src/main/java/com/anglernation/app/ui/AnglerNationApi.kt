package com.anglernation.app.ui

import com.squareup.retrofit2.http.GET

interface AnglerNationApi {
    @GET("rods")
    suspend fun getRods(): List<RodDto>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8081/"
    }
}