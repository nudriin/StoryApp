package com.nudriin.storyapp

import com.google.gson.annotations.SerializedName
import com.nudriin.storyapp.data.dto.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                id = i.toString(),
                photoUrl = "photoUrl + $i",
                createdAt = "photoUrl + $i",
                name = "photoUrl + $i",
                description = "photoUrl + $i",
                lon = i.toDouble(),
                lat = i.toDouble()
            )
            items.add(story)
        }
        return items
    }
}