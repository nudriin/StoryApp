package com.nudriin.storyapp.di

import android.content.Context
import com.nudriin.storyapp.data.pref.UserPreference
import com.nudriin.storyapp.data.pref.dataStore
import com.nudriin.storyapp.data.repository.AuthRepository
import com.nudriin.storyapp.data.retrofit.ApiConfig

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(userPreference, apiService)
    }
}