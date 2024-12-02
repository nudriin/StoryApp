package com.nudriin.storyapp.common

import androidx.lifecycle.ViewModel
import com.nudriin.storyapp.data.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {
    fun getAllStories() = storyRepository.getAllStories()

    fun addStory(description: RequestBody, file: MultipartBody.Part) =
        storyRepository.addStory(description, file)
}