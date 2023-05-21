package com.example.todoapp.model

import androidx.lifecycle.MutableLiveData
import com.example.todoapp.dto.LoginModule
import com.example.todoapp.dto.LoginResponse
import com.example.todoapp.dto.RegisterModule
import com.example.todoapp.dto.RegisterResponse
import com.example.todoapp.repository.AuthRepository
import com.example.todoapp.service.ApiResponse
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel(), ViewModelComponent
{

    private val _loginResponse = MutableLiveData<ApiResponse<LoginResponse>>()
    private val _registerResponse = MutableLiveData<ApiResponse<RegisterResponse>>()
    val loginResponse = _loginResponse
    val registerResponse = _registerResponse

    fun login(auth: LoginModule, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _loginResponse,
        coroutinesErrorHandler
    ){
        authRepository.login(auth)
    }

    fun register(auth: RegisterModule, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _registerResponse,
        coroutinesErrorHandler
    ){
        authRepository.register(auth)
    }
}