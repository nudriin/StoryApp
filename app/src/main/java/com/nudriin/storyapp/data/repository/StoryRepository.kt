package com.nudriin.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.nudriin.storyapp.data.StoryPagingSource
import com.nudriin.storyapp.data.dto.response.ListStoryItem
import com.nudriin.storyapp.data.dto.response.Response
import com.nudriin.storyapp.data.pref.UserPreference
import com.nudriin.storyapp.data.retrofit.ApiService
import com.nudriin.storyapp.utils.MyResult
import com.nudriin.storyapp.utils.toJWT
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class StoryRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    fun getAllStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(userPreference, apiService)
            }
        ).liveData
    }
    
    fun addStory(description: RequestBody, file: MultipartBody.Part) = liveData {
        emit(MyResult.Loading)
        try {
            val response =
                apiService.addStories(
                    token = userPreference.getSession().first().token.toJWT(),
                    description = description,
                    file = file
                )
            emit(MyResult.Success(response))
        } catch (e: HttpException) {
            val response = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(response, Response::class.java)
            emit(MyResult.Error(body.message))
        } catch (e: Exception) {
            emit(MyResult.Error(e.message ?: "An error occurred"))
        }
    }

    fun getAllStoriesWithLocation() = liveData {
        emit(MyResult.Loading)
        try {
            val response =
                apiService.getStoriesWithLocation(userPreference.getSession().first().token.toJWT())
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
        private var instance: StoryRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(userPreference, apiService)
            }.also { instance = it }
    }
}