package com.nudriin.storyapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nudriin.storyapp.R
import com.nudriin.storyapp.common.AuthViewModel
import com.nudriin.storyapp.databinding.ActivityMainBinding
import com.nudriin.storyapp.utils.ViewModelFactory
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        authViewModel.getSession().observe(this) { session ->
            if (session.token.isEmpty()) {
//                val intent = Intent(this, WelcomeActivity::class.java)
//                intent.flags =
//                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(intent)
            }
        }
    }
}