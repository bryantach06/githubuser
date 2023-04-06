package com.example.githubuser.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
data class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = false)
//    var id: Int? = null,
    var username: String = "",
    var avatarUrl: String? = null,
    var isFavorite: Boolean = false,
)
