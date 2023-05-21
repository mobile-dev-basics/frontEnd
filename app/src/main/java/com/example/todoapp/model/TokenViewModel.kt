package com.example.todoapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.singleton.TokenManager
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager
): ViewModel() {
    val token = MutableLiveData<String?>()

    init{
        viewModelScope.launch ( Dispatchers.IO ){
            tokenManager.getToken().collect{
                withContext(Dispatchers.Main){
                    token.value = it
                }
            }
        }
    }

    fun saveToken(token: String){
        viewModelScope.launch (Dispatchers.IO){
            tokenManager.saveToken(token)
        }
    }

    fun deleteToken(){
        viewModelScope.launch (Dispatchers.IO){
            tokenManager.deleteToken()
        }
    }
}