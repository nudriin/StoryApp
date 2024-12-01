package com.nudriin.storyapp.common

import androidx.lifecycle.ViewModel
import com.nudriin.storyapp.data.repository.StoryRepository

class StoryViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {
    fun getAllStories() = storyRepository.getAllStories()

    fun getStoriesById(id: String) = storyRepository.getStoriesById(id)
}