package com.example.githubuser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.ui.adapters.FollowAdapter
import com.example.githubuser.ui.viewmodels.FollowViewModel
import com.example.githubuser.databinding.FragmentFollowingAndFollowersBinding

class FollowingAndFollowersFragment : Fragment() {

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "login"
    }

    private lateinit var binding: FragmentFollowingAndFollowersBinding

    private var position: Int = 0
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingAndFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollow.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = FollowAdapter(emptyList())
        }

        val followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]
        val userName = arguments?.getString("login")

        if (position == 0){
            if (userName != null) {
                followViewModel.getUserFollowers(userName)
            }
        } else {
            if (userName != null) {
                followViewModel.getUserFollowings(userName)
            }
        }

        followViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        followViewModel.followingResponse.observe(viewLifecycleOwner) {
            (binding.rvFollow.adapter as FollowAdapter).updateData(it)
        }

        followViewModel.followerResponse.observe(viewLifecycleOwner) {
            (binding.rvFollow.adapter as FollowAdapter).updateData(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.tabProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}