package com.example.todoapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityLoginBinding
import com.example.todoapp.dto.LoginModule
import com.example.todoapp.model.AuthViewModel
import com.example.todoapp.model.CoroutinesErrorHandler
import com.example.todoapp.model.TokenViewModel
import com.example.todoapp.service.ApiResponse
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityLoginBinding
    private val viewModel : AuthViewModel by viewModels()
    private val tokenViewModel : TokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        val loginTV = binding.loginTV
        setContentView(view)

        tokenViewModel.token.observe(this){
            token -> if (token != null)
                toMainPage()
        }

        viewModel.loginResponse.observe(this){
            when(it){
                is ApiResponse.Failure -> {
                    loginTV.text = it.errorMessage
                    println(it.errorMessage)
                }
                is ApiResponse.Loading -> {
                    loginTV.setTextColor(Color.GRAY)
                    loginTV.text = "Loading"
                }
                is ApiResponse.Success -> {
                    tokenViewModel.saveToken(it.data.token)

                }
            }
        }
        binding.siginBtn.setOnClickListener(this)
        binding.toRegister.setOnClickListener(this)
    }

    private fun toMainPage() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.siginBtn -> {
                login()
            }
            R.id.toRegister -> {
                goToRegisterActivity()
            }

        }
    }

    private fun goToRegisterActivity() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun login() {
        val email = binding.fieldEmail.editText?.text.toString()
        val password = binding.fieldPassword.editText?.text.toString()
        val data = LoginModule(email, password)

        viewModel.login(data, object: CoroutinesErrorHandler{
            override fun onError(message: String) {
                binding.loginTV.text = "Error! $message"
            }
        })
    }
}


