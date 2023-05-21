package com.example.todoapp.repository

import com.example.todoapp.dto.LoginModule
import com.example.todoapp.dto.RegisterModule
import com.example.todoapp.dto.RegisterResponse
import com.example.todoapp.service.ApiResponse
import com.example.todoapp.service.AuthApiService
import com.example.todoapp.service.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApiService: AuthApiService) {
    fun login(auth: LoginModule) = apiRequestFlow {
        authApiService.login(auth)
    }

    fun register(auth: RegisterModule) : Flow<ApiResponse<RegisterResponse>> = apiRequestFlow {
        authApiService.register(auth)
    }
}