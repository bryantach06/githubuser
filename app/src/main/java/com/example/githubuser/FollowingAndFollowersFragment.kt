package com.example.githubuser

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
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
    ): View? {
        binding = FragmentFollowingAndFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }
//        if (position == 1){
//            binding.rvFollow.adapter = FollowAdapter(listFollow = List<FollowResponseItem>())
//        } else {
//            binding.tvTest.text = "Following : $username"
//            Log.d("Username", username)
//        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.tabProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}