package com.example.todoapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.todoapp.singleton.Queue
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityRegisterBinding
import com.example.todoapp.dto.RegisterModule
import com.example.todoapp.model.AuthViewModel
import com.example.todoapp.model.CoroutinesErrorHandler
import com.example.todoapp.service.ApiResponse
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import org.json.JSONObject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityRegisterBinding
    private val viewModel : AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.registerResponse.observe(this){
            when(it){
                is ApiResponse.Failure -> {
                    binding.loginTV.text = it.errorMessage
                }
                is ApiResponse.Loading -> {
                    binding.loginTV.text = "Loading"
                }
                is ApiResponse.Success ->{
                    goToLogin()
                }
            }
        }
        binding.signupButton.setOnClickListener(this)
        binding.login.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.signupButton -> {
                register()
            }
            R.id.login ->{
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun register() {
        val name = binding.fieldName.editText?.text.toString()
        val email = binding.fieldEmail.editText?.text.toString()
        val password = binding.fieldPassword.editText?.text.toString()
        val data = RegisterModule(name, email, password)

        viewModel.register(data, object: CoroutinesErrorHandler{
            override fun onError(message: String) {
                binding.loginTV.text = "Error! $message"
            }
        })

    }
}