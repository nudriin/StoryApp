package com.nudriin.storyapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.nudriin.storyapp.data.pref.UserPreference
import com.nudriin.storyapp.data.pref.dataStore
import com.nudriin.storyapp.data.retrofit.ApiConfig
import com.nudriin.storyapp.utils.toJWT
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val userPreference = UserPreference.getInstance(mContext.dataStore)
    private val apiService = ApiConfig.getApiService()

    private val mWidgetItems = ArrayList<Bitmap>()
    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        mWidgetItems.clear()
        runBlocking {
            try {
                val token = userPreference.getSession().first().token.toJWT()
                val response = apiService.getAllStories(token)
                response.listStory.forEach { story ->
                    val imageUrl = story.photoUrl
                    val bitmap = loadImageFromUrl(imageUrl)
                    bitmap?.let { mWidgetItems.add(it) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])
        val extras = bundleOf(
            BannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false
    private fun loadImageFromUrl(url: String): Bitmap? {
        return try {
            Glide.with(mContext)
                .asBitmap()
                .load(url)
                .submit()
                .get()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

