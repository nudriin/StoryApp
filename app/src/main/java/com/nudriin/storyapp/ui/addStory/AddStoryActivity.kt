package com.nudriin.storyapp.ui.addStory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nudriin.storyapp.databinding.ActivityAddStoryBinding

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}