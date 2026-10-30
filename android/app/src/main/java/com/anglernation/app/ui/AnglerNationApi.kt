package com.anglernation.app.ui

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.ListAdapter
import com.squareup.retrofit2.GET
import com.squareup.retrofit2.Header
import com.squareup.retrofit2.POST
import com.squareup.retrofit2.PUT
import com.squareup.retrofit2.DELETE
import com.squareup.retrofit2.Path
import com.squareup.retrofit2.Query
import com.squareup.retrofit2.Part
import com.squareup.retrofit2.Response
import com.squareup.retrofit2.Retrofit
import com.squareup.retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.retrofit2.OkHttpClient
import com.squareup.okhttp3.logging.HttpLoggingInterceptor
import com.anglernation.app.ui.auth.AuthInterceptor
import java.io.IOException
import java.util.List
import java.util.UUID

interface AnglerNationApi {

    // Rods CRUD + pagination
    @GET("api/rods")
    suspend fun getRods(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): ListResponse<RodDto>

    @POST("api/rods")
    suspend fun createRod(@Body request: CreateRodRequest): RodDto

    @PUT("api/rods/{id}")
    suspend fun updateRod(
        @Path("id") id: UUID,
        @Body request: UpdateRodRequest
    ): RodDto

    @DELETE("api/rods/{id}")
    suspend fun deleteRod(@Path("id") id: UUID)

    // Lures CRUD + pagination
    @GET("api/lures")
    suspend fun getLures(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): ListResponse<LureDto>

    @POST("api/lures")
    suspend fun createLure(@Body request: CreateLureRequest): LureDto

    @PUT("api/lures/{id}")
    suspend fun updateLure(
        @Path("id") id: UUID,
        @Body request: UpdateLureRequest
    ): LureDto

    @DELETE("api/lures/{id}")
    suspend fun deleteLure(@Path("id") id: UUID)

    // Lines CRUD + pagination
    @GET("api/lines")
    suspend fun getLines(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): ListResponse<LineDto>

    @POST("api/lines")
    suspend fun createLine(@Body request: CreateLineRequest): LineDto

    @PUT("api/lines/{id}")
    suspend fun updateLine(
        @Path("id") id: UUID,
        @Body request: UpdateLineRequest
    ): LineDto

    @DELETE("api/lines/{id}")
    suspend fun deleteLine(@Path("id") id: UUID)

    // Locations CRUD + pagination
    @GET("api/locations")
    suspend fun getLocations(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): ListResponse<LocationDto>

    @POST("api/locations")
    suspend fun createLocation(@Body request: CreateLocationRequest): LocationDto

    @PUT("api/locations/{id}")
    suspend fun updateLocation(
        @Path("id") id: UUID,
        @Body request: UpdateLocationRequest
    ): LocationDto

    @DELETE("api/locations/{id}")
    suspend fun deleteLocation(@Path("id") id: UUID)

    // Fish CRUD + pagination
    @GET("api/fish")
    suspend fun getFish(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): ListResponse<FishDto>

    @POST("api/fish")
    suspend fun createFish(@Body request: CreateFishRequest): FishDto

    @PUT("api/fish/{id}")
    suspend fun updateFish(
        @Path("id") id: UUID,
        @Body request: UpdateFishRequest
    ): FishDto

    @DELETE("api/fish/{id}")
    suspend fun deleteFish(@Path("id") id: UUID)

    // Catches CRUD + pagination
    @GET("api/catches")
    suspend fun getCatches(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): ListResponse<CatchDto>

    @POST("api/catches")
    suspend fun createCatch(@Body request: CreateCatchRequest): CatchDto

    @PUT("api/catches/{id}")
    suspend fun updateCatch(
        @Path("id") id: UUID,
        @Body request: UpdateCatchRequest
    ): CatchDto

    @DELETE("api/catches/{id}")
    suspend fun deleteCatch(@Path("id") id: UUID)

    // Posts CRUD + pagination
    @GET("api/posts")
    suspend fun getPosts(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): ListResponse<PostDto>

    @POST("api/posts")
    suspend fun createPost(@Body request: CreatePostRequest): PostDto

    @PUT("api/posts/{id}")
    suspend fun updatePost(
        @Path("id") id: UUID,
        @Body request: UpdatePostRequest
    ): PostDto

    @DELETE("api/posts/{id}")
    suspend fun deletePost(@Path("id") id: UUID)

    // Comments CRUD + pagination
    @GET("api/comments")
    suspend fun getComments(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): ListResponse<CommentDto>

    @POST("api/comments")
    suspend fun createComment(@Body request: CreateCommentRequest): CommentDto

    @PUT("api/comments/{id}")
    suspend fun updateComment(
        @Path("id") id: UUID,
        @Body request: UpdateCommentRequest
    ): CommentDto

    @DELETE("api/comments/{id}")
    suspend fun deleteComment(@Path("id") id: UUID)

    // Auth
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    data class ListResponse<T>(
        val content: List<T>,
        val totalElements: Long,
        val totalPages: Int,
        val number: Int
    )

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8081/"

        private val moshi: Moshi by lazy {
            Moshi.Builder()
                .add(com.squareup.moshi.KotlinJsonAdapterFactory())
                .build()
        }

        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(AuthInterceptor())
                .build()
        }

        val create: AnglerNationApi
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(AnglerNationApi::class.java)
    }
}