package com.example.githubuser.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.ItemsItem
import com.example.githubuser.database.FavoriteUserEntity
import com.example.githubuser.database.SettingsPreferences
import com.example.githubuser.databinding.ActivityFavoriteUserListBinding
import com.example.githubuser.ui.adapters.UsersAdapter
import com.example.githubuser.ui.viewmodels.FavoriteUserListViewModel
import com.example.githubuser.ui.viewmodels.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class FavoriteUserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserListBinding
    private lateinit var viewModel: FavoriteUserListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(application, SettingsPreferences.getInstance(dataStore))).get(FavoriteUserListViewModel::class.java)

        viewModel.getAllFavorites().observe(this){user : List<FavoriteUserEntity>? ->
            val items = arrayListOf<ItemsItem>()
            if (user != null) {
                user.map{
                    val item = ItemsItem(
                        login = it.username,
                        avatarUrl = it.avatarUrl ?: ""
                    )
                    items.add(item)
                }
            }
            binding.rvFavuser.adapter = UsersAdapter(items)
            binding.rvFavuser.layoutManager = LinearLayoutManager(this)
        }
    }
}
