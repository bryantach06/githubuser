package com.example.githubuser.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null,
    var isFavorite: Boolean = false,
)
