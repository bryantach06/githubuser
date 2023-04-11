package com.example.githubuser.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoriteUserEntity
import com.example.githubuser.repository.FavoriteUserRepository

class FavoriteUserListViewModel(application: Application): ViewModel() {

    private val mFavoriteUserRepository : FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavorites(): LiveData<List<FavoriteUserEntity>> {
        return mFavoriteUserRepository.getAllFavorites()
    }
}
