package com.example.githubuser.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.ui.viewmodels.DetailViewModel
import com.example.githubuser.ui.fragments.FollowingAndFollowersFragment.Companion.ARG_USERNAME
import com.example.githubuser.R
import com.example.githubuser.ui.adapters.SectionsPagerAdapter
import com.example.githubuser.api.UserResponse
import com.example.githubuser.database.FavoriteUserEntity
import com.example.githubuser.database.SettingsPreferences
import com.example.githubuser.ui.adapters.UsersAdapter.Companion.EXTRA_USER
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.ui.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailUserActivity : AppCompatActivity() {

    private var detailBinding: ActivityDetailUserBinding? = null
    private val binding get() = detailBinding

    private var isFavorite = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val favUser = FavoriteUserEntity()
        val username = intent.getStringExtra(ARG_USERNAME) ?: ""
        val adapter = SectionsPagerAdapter(this, username)
        val viewPager = binding?.viewPager
        viewPager?.adapter = adapter

        val tabLayout = binding?.tabs
        if (tabLayout != null) {
            if (viewPager != null) {
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    when (position) {
                        0 -> tab.text = "Followers"
                        1 -> tab.text = "Following"
                    }
                }.attach()
            }
        }

        val viewModel = obtainViewModel(this@DetailUserActivity)
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

        viewModel.getFavoriteUserByUsername(username).observe(this){favoriteUser ->
            if (favoriteUser == null) {
                binding?.btnFavorite?.setImageResource(R.drawable.baseline_favorite_border_24)
                isFavorite = false
            } else {
                binding?.btnFavorite?.setImageResource(R.drawable.baseline_favorite_24)
                isFavorite = true
            }
        }

        binding?.btnFavorite?.setOnClickListener {
            val uname = user?.login
            val avatar = user?.avatarUrl

            if (isFavorite) {
                if (uname != null) {
                    viewModel.deleteByUsername(uname)
                }
            } else {
                uname?.let { it1 -> FavoriteUserEntity(it1, avatar, true) }
                    ?.let { it2 -> viewModel.insert(it2) }
            }

            favUser.let {
                if (uname != null) {
                    it.username = uname
                }
                it.avatarUrl = avatar
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        detailBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, SettingsPreferences.getInstance(dataStore))
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.detailProgressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
