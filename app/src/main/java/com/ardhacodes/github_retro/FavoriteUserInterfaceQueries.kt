package com.ardhacodes.github_retro

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserInterfaceQueries {
    @Insert
    suspend fun addToFavorite(favoriteUser: FavoriteUserEntity)

    @Query("SELECT * FROM table_favoriteuser")
    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT count(*) FROM table_favoriteuser WHERE table_favoriteuser.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM table_favoriteuser WHERE table_favoriteuser.id = :id")
    suspend fun removeFromFavorite(id: Int): Int

    @Query("SELECT * FROM table_favoriteuser")
    fun FindAllData(): Cursor
}