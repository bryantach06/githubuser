package com.example.githubuser.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityFavoriteUserListBinding
import com.example.githubuser.ui.viewmodels.FavoriteUserListViewModel

class FavoriteUserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[FavoriteUserListViewModel::class.java]
    }
}
