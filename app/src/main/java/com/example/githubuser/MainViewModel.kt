package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    val _usersResponse = MutableLiveData<GithubResponse>()
    val usersResponse : LiveData<GithubResponse> = _usersResponse

    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers : LiveData<List<ItemsItem>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val QUERY_USERNAME = "arif"
    }

    init {
        findUsers()
    }

    private fun findUsers(){
        _isLoading.value = true
        ApiConfig.getApiService().getUsers(query = QUERY_USERNAME).enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _usersResponse.value = response.body()
                    _listUsers.value = response.body()?.items
                } else{
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("TAG", "${t.message}")
            }
        })
    }

}