package com.example.githubuser.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.*
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.ui.adapters.UsersAdapter
import com.example.githubuser.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
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

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
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
        val favoriteList = menu.findItem(R.id.favorites)
        favoriteList.setOnMenuItemClickListener {
            startActivity(Intent(this, FavoriteUserListActivity::class.java))
            true
        }
        return true
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