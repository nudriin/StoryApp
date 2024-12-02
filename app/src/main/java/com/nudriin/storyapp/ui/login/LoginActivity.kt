package com.nudriin.storyapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nudriin.storyapp.common.AuthViewModel
import com.nudriin.storyapp.data.dto.request.UserLoginRequest
import com.nudriin.storyapp.data.pref.UserModel
import com.nudriin.storyapp.databinding.ActivityLoginBinding
import com.nudriin.storyapp.ui.MainActivity
import com.nudriin.storyapp.utils.MyResult
import com.nudriin.storyapp.utils.ViewModelFactory
import com.nudriin.storyapp.utils.showToast

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.getSession().observe(this) { session ->
            if (session.token.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

        setupAction()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            authViewModel.login(
                UserLoginRequest(
                    email = email,
                    password = password
                )
            ).observe(this) { result ->
                when (result) {
                    is MyResult.Loading -> {
                        binding.btnLogin.isEnabled = false
                        showLoading(true)
                    }

                    is MyResult.Success -> {
                        showLoading(false)
                        showToast(this, result.data.message)
                        binding.btnLogin.isEnabled = true

                        authViewModel.saveSession(
                            UserModel(
                                id = result.data.loginResult.userId,
                                name = result.data.loginResult.name,
                                token = result.data.loginResult.token,
                                isLogin = true
                            )
                        )

                        Log.d("LoginActivity", result.data.toString())
                        startActivity(Intent(this, MainActivity::class.java))
                    }

                    is MyResult.Error -> {
                        showLoading(false)
                        binding.btnLogin.isEnabled = true
                        showToast(this, result.error)
                        Log.d("LoginActivity", result.error)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}