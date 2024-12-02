package com.nudriin.storyapp.ui.storyDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.nudriin.storyapp.databinding.FragmentStoryDetailBinding

class StoryDetailFragment : Fragment() {
    private var _binding: FragmentStoryDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        val storyData = StoryDetailFragmentArgs.fromBundle(arguments as Bundle)
        Glide.with(this).load(storyData.photoUrl).into(binding.ivDetailPhoto)

        with(binding) {
            tvDetailName.text = storyData.name
            tvDetailDescription.text = storyData.description
        }
    }

}