package com.example.todoapp.service

import com.example.todoapp.dto.LoginModule
import com.example.todoapp.dto.LoginResponse
import com.example.todoapp.dto.RegisterModule
import com.example.todoapp.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/login")
    suspend fun login(
        @Body auth: LoginModule
    ) : Response<LoginResponse>

    @GET("/api/refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String
    ) : Response<LoginResponse>

    @POST("/api/register")
    suspend fun register(
        @Body auth: RegisterModule
    ) : Response<RegisterResponse>
}