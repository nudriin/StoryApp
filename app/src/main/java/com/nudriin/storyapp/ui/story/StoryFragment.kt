package com.nudriin.storyapp.ui.story

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nudriin.storyapp.R
import com.nudriin.storyapp.adapter.StoryAdapter
import com.nudriin.storyapp.common.AuthViewModel
import com.nudriin.storyapp.common.StoryViewModel
import com.nudriin.storyapp.data.dto.response.ListStoryItem
import com.nudriin.storyapp.databinding.FragmentStoryBinding
import com.nudriin.storyapp.ui.addStory.AddStoryActivity
import com.nudriin.storyapp.ui.login.LoginActivity
import com.nudriin.storyapp.utils.MyResult
import com.nudriin.storyapp.utils.ViewModelFactory
import com.nudriin.storyapp.utils.showToast

class StoryFragment : Fragment() {
    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!
    private val storyViewModel: StoryViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupAction()
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(context)
        binding.rvStory.layoutManager = layoutManager

        storyViewModel.getAllStories().observe(viewLifecycleOwner) { result ->
            when (result) {
                is MyResult.Loading -> {
                    showLoading(true)
                }

                is MyResult.Success -> {
                    showLoading(false)
                    setStoriesList(result.data.listStory)
                }

                is MyResult.Error -> {
                    showLoading(false)
                    showToast(requireContext(), result.error)
                }
            }
        }
    }

    private fun setupAction() {
        binding.topAppBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.logout -> {
                    authViewModel.logout()
                    true
                }

                else -> false
            }
        }

        binding.btnAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddStoryActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun setStoriesList(storiesList: List<ListStoryItem>) {
        val adapter = StoryAdapter(storiesList)
        binding.rvStory.adapter = adapter
        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(name: String, description: String, photoUrl: String) {
                moveToStoryDetail(name, description, photoUrl)
            }
        })
    }

    private fun moveToStoryDetail(
        name: String,
        description: String,
        photoUrl: String
    ) {
        val toStoryDetail = StoryFragmentDirections.actionStoryFragmentToStoryDetailFragment3(
            name,
            description,
            photoUrl
        )

        Navigation.findNavController(binding.root).navigate(toStoryDetail)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}