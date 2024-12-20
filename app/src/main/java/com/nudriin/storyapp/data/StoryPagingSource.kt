package com.nudriin.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nudriin.storyapp.data.dto.response.ListStoryItem
import com.nudriin.storyapp.data.pref.UserPreference
import com.nudriin.storyapp.data.retrofit.ApiService
import com.nudriin.storyapp.utils.toJWT
import kotlinx.coroutines.flow.first

class StoryPagingSource(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) :
    PagingSource<Int, ListStoryItem>() {

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllStories(
                token = userPreference.getSession().first().token.toJWT(),
                page = position,
                size = params.loadSize
            )
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.listStory.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}