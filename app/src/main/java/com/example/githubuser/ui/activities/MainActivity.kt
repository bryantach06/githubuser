package com.example.githubuser.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.*
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.database.SettingsPreferences
import com.example.githubuser.ui.adapters.UsersAdapter
import com.example.githubuser.ui.viewmodels.MainViewModel
import com.example.githubuser.ui.viewmodels.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var favoriteList: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(application, SettingsPreferences.getInstance(dataStore)))[MainViewModel::class.java]
        mainViewModel.listUsers.observe(this) { listUsers ->
            val adapter = UsersAdapter(listUsers)
            binding.rvUsers.adapter = adapter
            val layoutManager = LinearLayoutManager(this)
            binding.rvUsers.layoutManager = layoutManager
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showUsers(listUsers: List<ItemsItem>) {
        val adapter = UsersAdapter(listUsers)
        binding.rvUsers.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val switch = menu.findItem(R.id.theme_switch).actionView as SwitchMaterial
        val pref = SettingsPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(application, pref))[MainViewModel::class.java]
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switch.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switch.isChecked = false
            }
        }

        switch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        favoriteList = menu.findItem(R.id.favorites)
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(que: String): Boolean {
                showLoading(true)
                getUserSearchData(query = que)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item == favoriteList) {
            startActivity(Intent(this, FavoriteUserListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserSearchData (query : String){
        ApiConfig.getApiService().getUsers(query = query).enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val filteredUsers = response.body()?.items?.filter { it.login.contains(query, true) }
                    showUsers(filteredUsers ?: emptyList())
                }else{
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.d("TAG", "${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}