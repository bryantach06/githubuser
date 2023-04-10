package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUserEntity)

    @Update
    fun update(favoriteUser: FavoriteUserEntity)

    @Delete
    fun delete(favoriteUser: FavoriteUserEntity)

    @Query("SELECT * FROM FavoriteUserEntity WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserEntity>

    @Query("DELETE FROM FavoriteUserEntity WHERE isFavorite = true AND username = :username")
    fun deleteByUsername(username: String)

    @Query("SELECT * FROM FavoriteUserEntity")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>>
}
