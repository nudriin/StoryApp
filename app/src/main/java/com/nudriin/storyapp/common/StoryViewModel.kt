package com.nudriin.storyapp.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nudriin.storyapp.data.dto.response.ListStoryItem
import com.nudriin.storyapp.data.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {

    val stories: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getAllStories().cachedIn(viewModelScope)

    fun addStory(description: RequestBody, file: MultipartBody.Part) =
        storyRepository.addStory(description, file)

    fun getAllStoriesWithLocation() = storyRepository.getAllStoriesWithLocation()
}