package com.nudriin.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.nudriin.storyapp.data.dto.request.UserLoginRequest
import com.nudriin.storyapp.data.dto.request.UserRegisterRequest
import com.nudriin.storyapp.data.dto.response.Response
import com.nudriin.storyapp.data.pref.UserModel
import com.nudriin.storyapp.data.pref.UserPreference
import com.nudriin.storyapp.data.retrofit.ApiService
import com.nudriin.storyapp.utils.MyResult
import retrofit2.HttpException

class AuthRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun saveSession(userModel: UserModel) {
        userPreference.saveSession(userModel)
    }

    fun getSession(): LiveData<UserModel> {
        return userPreference.getSession().asLiveData()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun login(request: UserLoginRequest) = liveData {
        emit(MyResult.Loading)
        try {
            val response = apiService.login(request)
            emit(MyResult.Success(response))
        } catch (e: HttpException) {
            val response = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(response, Response::class.java)
            emit(MyResult.Error(body.message))
        } catch (e: Exception) {
            emit(MyResult.Error(e.message ?: "An error occurred"))
        }
    }

    fun register(request: UserRegisterRequest) = liveData {
        emit(MyResult.Loading)
        try {
            val response = apiService.register(request)
            emit(MyResult.Success(response))
        } catch (e: HttpException) {
            val response = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(response, Response::class.java)
            emit(MyResult.Error(body.message))
        } catch (e: Exception) {
            emit(MyResult.Error(e.message ?: "An error occurred"))
        }
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(userPreference, apiService)
            }.also { instance = it }
    }
}