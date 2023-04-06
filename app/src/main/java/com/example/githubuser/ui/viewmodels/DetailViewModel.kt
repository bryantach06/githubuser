package com.example.githubuser.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.ApiService
import com.example.githubuser.api.UserResponse
import com.example.githubuser.database.FavoriteUserEntity
import com.example.githubuser.repository.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailViewModel(application: Application): ViewModel(){

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    val _userDetail = MutableLiveData<UserResponse>()
    val userDetail : LiveData<UserResponse> = _userDetail

    fun insert(favorite: FavoriteUserEntity) {
        mFavoriteUserRepository.insert(favorite)
    }

    fun update(favorite: FavoriteUserEntity) {
        mFavoriteUserRepository.update(favorite)
    }

    fun delete(favorite: FavoriteUserEntity) {
        mFavoriteUserRepository.delete(favorite)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity> {
        return mFavoriteUserRepository.getAllFavorites(username)
    }

    fun deleteByUsername(username: String){
        return mFavoriteUserRepository.deleteByUsername(username)
    }

    fun getDetailUser(username: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        service.getDetailUser(username).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful){
                    _userDetail.postValue(response.body())
                } else {
                    Log.e("UserDetailViewModel", "Error fetching user detail: ${response.message()}, ${response.code()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("UserDetailViewModel", "Error fetching user detail", t)
            }
        })
    }
}