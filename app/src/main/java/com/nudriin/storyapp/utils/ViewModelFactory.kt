package com.nudriin.storyapp.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nudriin.storyapp.common.AuthViewModel
import com.nudriin.storyapp.data.repository.AuthRepository
import com.nudriin.storyapp.di.Injection

class ViewModelFactory(
    private val authRepository: AuthRepository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(authRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                val instance = ViewModelFactory(
                    Injection.provideAuthRepository(context),
                )
                INSTANCE = instance
                instance
            }
        }
    }
}