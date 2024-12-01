package com.nudriin.storyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nudriin.storyapp.data.dto.response.ListStoryItem
import com.nudriin.storyapp.databinding.StoryCardBinding

class StoryAdapter(private val storyList: List<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            StoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = storyList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = storyList[position]
        holder.bind(story)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(
                storyId = story.id,
            )
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(
            storyId: String,
        )
    }

    class ViewHolder(private val binding: StoryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            with(binding) {
                Glide.with(root.context)
                    .load(story.photoUrl)
                    .into(ivStoryThumbnail)

                tvStoryTitle.text = story.name

                tvStoryDescription.text = story.description
            }
        }

    }
}