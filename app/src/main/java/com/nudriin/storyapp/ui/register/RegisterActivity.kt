package com.nudriin.storyapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nudriin.storyapp.R
import com.nudriin.storyapp.common.AuthViewModel
import com.nudriin.storyapp.data.dto.request.UserRegisterRequest
import com.nudriin.storyapp.databinding.ActivityRegisterBinding
import com.nudriin.storyapp.ui.MainActivity
import com.nudriin.storyapp.utils.MyResult
import com.nudriin.storyapp.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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
        binding.btnRegister.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            authViewModel.register(
                UserRegisterRequest(
                    name = name,
                    email = email,
                    password = password
                )
            ).observe(this) { result ->
                when (result) {
                    is MyResult.Loading -> {
                        binding.btnRegister.isEnabled = false
                    }

                    is MyResult.Success -> {
                        Log.d("RegisterActivity", result.data.toString())
                        binding.btnRegister.isEnabled = true
//                        startActivity(Intent(this, LoginActivity::class.java))
                    }

                    is MyResult.Error -> {
                        binding.btnRegister.isEnabled = true
                        Log.d("RegisterActivity", result.error)

//                        result.error.getContentIfNotHandled().let { toastText ->
//                            showToast(this, toastText.toString())
//                        }
                    }
                }
            }
        }


    }


}