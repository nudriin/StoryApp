package com.nudriin.storyapp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nudriin.storyapp.data.dto.request.UserLoginRequest
import com.nudriin.storyapp.data.dto.request.UserRegisterRequest
import com.nudriin.storyapp.data.pref.UserModel
import com.nudriin.storyapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun saveSession(userModel: UserModel) {
        viewModelScope.launch {
            authRepository.saveSession(userModel)
        }
    }

    fun getSession() = authRepository.getSession()

    fun register(request: UserRegisterRequest) = authRepository.register(request)

    fun login(request: UserLoginRequest) = authRepository.login(request)

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}