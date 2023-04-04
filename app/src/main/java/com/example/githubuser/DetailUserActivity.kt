package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.FollowingAndFollowersFragment.Companion.ARG_USERNAME
import com.example.githubuser.UsersAdapter.Companion.EXTRA_USER
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(ARG_USERNAME) ?: ""
        val adapter = SectionsPagerAdapter(this, username)
        val viewPager = binding.viewPager
        viewPager.adapter = adapter

        val tabLayout = binding.tabs
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Followers"
                1 -> tab.text = "Following"
            }
        }.attach()

        val viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        val tv_name = findViewById<TextView>(R.id.name)
        val avatar_user = findViewById<ImageView>(R.id.userPhoto)
        val tv_username = findViewById<TextView>(R.id.username)
        val tv_followers = findViewById<TextView>(R.id.followers)
        val tv_following = findViewById<TextView>(R.id.following)

        val user = intent.getParcelableExtra<UserResponse?>(EXTRA_USER)

        if (user != null) {
            viewModel.getDetailUser(user.login)
        }

        viewModel.userDetail.observe(this) {
            showLoading(true)
            if (it != null) {
                showLoading(false)
                Glide.with(this)
                    .load(it.avatarUrl)
                    .into(avatar_user)
                tv_name.text = it.name
                tv_username.text = it.login
                tv_followers.text = "Followers : ${it.followers.toString()}"
                tv_following.text = "Following : ${it.following.toString()}"
                print(tv_name)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.detailProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
