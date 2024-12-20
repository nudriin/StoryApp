package com.nudriin.storyapp.ui.story

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nudriin.storyapp.R
import com.nudriin.storyapp.adapter.LoadingStateAdapter
import com.nudriin.storyapp.adapter.StoryAdapter
import com.nudriin.storyapp.common.AuthViewModel
import com.nudriin.storyapp.common.StoryViewModel
import com.nudriin.storyapp.databinding.FragmentStoryBinding
import com.nudriin.storyapp.ui.addStory.AddStoryActivity
import com.nudriin.storyapp.ui.maps.MapsActivity
import com.nudriin.storyapp.utils.ViewModelFactory

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

        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        storyViewModel.stories.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(name: String, description: String, photoUrl: String) {
                moveToStoryDetail(name, description, photoUrl)
            }
        })
    }

    private fun setupAction() {
        binding.topAppBar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.action_logout -> {
                    authViewModel.logout()
                    true
                }

                R.id.action_language -> {
                    startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                    true
                }

                R.id.action_maps -> {
                    val intent = Intent(requireContext(), MapsActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

        binding.btnAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddStoryActivity::class.java)
            startActivity(intent)
        }
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
}