package com.nudriin.storyapp.data.retrofit

import com.nudriin.storyapp.data.dto.request.UserLoginRequest
import com.nudriin.storyapp.data.dto.request.UserRegisterRequest
import com.nudriin.storyapp.data.dto.response.DetailStoryResponse
import com.nudriin.storyapp.data.dto.response.GetAllStoryResponse
import com.nudriin.storyapp.data.dto.response.Response
import com.nudriin.storyapp.data.dto.response.UserLoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: UserRegisterRequest): Response

    @POST("login")
    suspend fun login(@Body request: UserLoginRequest): UserLoginResponse

    @POST("stories")
    @Multipart()
    suspend fun addStories(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ): Response

    @GET("stories")
    suspend fun getAllStories(@Header("Authorization") token: String): GetAllStoryResponse

    @GET("/stories/{id}")
    suspend fun getStoriesById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): DetailStoryResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1,
    ): GetAllStoryResponse
}