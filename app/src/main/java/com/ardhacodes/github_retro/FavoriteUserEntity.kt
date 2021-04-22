package com.ardhacodes.github_retro

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "table_favoriteuser")
data class FavoriteUserEntity(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String,
    val type: String
):Serializable
