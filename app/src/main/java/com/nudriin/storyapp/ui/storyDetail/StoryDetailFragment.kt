package com.nudriin.storyapp.ui.storyDetail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
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
        playAnimation()
    }

    private fun setupView() {
        val storyData = StoryDetailFragmentArgs.fromBundle(arguments as Bundle)
        Glide.with(this).load(storyData.photoUrl).into(binding.ivDetailPhoto)

        with(binding) {
            tvDetailName.text = storyData.name
            tvDetailDescription.text = storyData.description
        }
    }

    @SuppressLint("Recycle")
    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.ivDetailPhoto, View.ALPHA, 1f).setDuration(300)
        val name = ObjectAnimator.ofFloat(binding.tvDetailName, View.ALPHA, 1f).setDuration(300)
        val description =
            ObjectAnimator.ofFloat(binding.tvDetailDescription, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(image, name, description)
            start()
        }
    }

}