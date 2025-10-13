package com.example.maneylink.dadesServidor


import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz que define los endpoints de la API de autenticación y gestión de usuarios.
 * Utiliza Retrofit para realizar peticiones HTTP asincrónicas.
 */
interface UserApi {

    @POST("users/login")
    suspend fun login(@Query("email") email: String, @Query("password") password: String): Response<LoginResponse>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): User

    @PUT("users/me/{id}")
    suspend fun updateProfile(@Path("id") id: String, @Body request: UpdateUserProfileRequest): User

    @POST("users")
    suspend fun addUser(@Body request: SignupRequest):  Response<LoginResponse>

    @POST("users/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>
}