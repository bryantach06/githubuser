package com.example.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.database.FavoriteUserDao
import com.example.githubuser.database.FavoriteUserEntity
import com.example.githubuser.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao : FavoriteUserDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favDao()
    }

    fun getAllFavorites(username: String): LiveData<FavoriteUserEntity> = mFavoriteUserDao.getFavoriteUserByUsername(username) //i need to figure out how to get the selected user's username

    fun insert(favorites: FavoriteUserEntity){
        executorService.execute{ mFavoriteUserDao.insert(favorites) }
    }

    fun delete(favorites: FavoriteUserEntity){
        executorService.execute { mFavoriteUserDao.delete(favorites) }
    }

    fun update(favorites: FavoriteUserEntity){
        executorService.execute { mFavoriteUserDao.update(favorites) }
    }
    fun deleteByUsername(username: String){
        executorService.execute { mFavoriteUserDao.deleteByUsername(username) }
    }
}