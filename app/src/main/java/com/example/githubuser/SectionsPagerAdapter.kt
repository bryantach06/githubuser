package com.example.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, var username: String): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowingAndFollowersFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowingAndFollowersFragment.ARG_POSITION, position)
            putString(FollowingAndFollowersFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}
