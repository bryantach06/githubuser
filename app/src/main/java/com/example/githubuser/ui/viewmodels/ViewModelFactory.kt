package com.example.githubuser.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.database.SettingsPreferences

class ViewModelFactory(private val mApplication: Application, private val pref: SettingsPreferences): ViewModelProvider.NewInstanceFactory(){
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application, pref: SettingsPreferences): ViewModelFactory{
            if (INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(application, pref)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteUserListViewModel::class.java)){
            return FavoriteUserListViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class: ${modelClass.name}")
    }
}
