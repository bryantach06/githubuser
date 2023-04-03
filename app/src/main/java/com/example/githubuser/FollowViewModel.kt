package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {

    companion object {
        private const val QUERY_USERNAME = ""
    }

    val _followResponse = MutableLiveData<FollowResponse>()
    val followResponse : LiveData<FollowResponse> = _followResponse

    private val _listFollow = MutableLiveData<List<FollowResponseItem>>()
    val listFollow : LiveData<List<FollowResponseItem>> = _listFollow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun getUsers(){
        _isLoading.value = true
        ApiConfig.getApiService().getUserFollowers(username = QUERY_USERNAME).enqueue(object: Callback<FollowResponse>{
            override fun onResponse(
                call: Call<FollowResponse>,
                response: Response<FollowResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _followResponse.value = response.body()
                    _listFollow.value = response.body()?.followResponse
                }
            }

            override fun onFailure(call: Call<FollowResponse>, t: Throwable) {
                Log.d("Follow Response", "${t.message}")
            }

        })
    }
}