package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {

    val _followerResponse = MutableLiveData<List<FollowResponseItem>>()
    val followerResponse : LiveData<List<FollowResponseItem>> = _followerResponse

    val _followingResponse = MutableLiveData<List<FollowResponseItem>>()
    val followingResponse : LiveData<List<FollowResponseItem>> = _followingResponse

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserFollowers(username: String){
        _isLoading.value = true
        ApiConfig.getApiService().getUserFollowers(username).enqueue(object: Callback<List<FollowResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _followerResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                Log.d("Follower Response", "${t.message}")
            }

        })
    }
    fun getUserFollowings(username: String){
        _isLoading.value = true
        ApiConfig.getApiService().getUserFollowing(username).enqueue(object : Callback<List<FollowResponseItem>>{
            override fun onResponse(
                call: Call<List<FollowResponseItem>>,
                response: Response<List<FollowResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _followingResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<FollowResponseItem>>, t: Throwable) {
                Log.d("Following Response", "${t.message}")
            }

        })
    }
}