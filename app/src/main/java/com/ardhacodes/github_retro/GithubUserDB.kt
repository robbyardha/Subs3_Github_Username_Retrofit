package com.ardhacodes.github_retro

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUserEntity::class],
    version = 1
)
abstract class GithubUserDB: RoomDatabase() {
    companion object{
        var INSTANCE: GithubUserDB?=null
        fun getDatabase(context: Context): GithubUserDB?{
            if(INSTANCE == null){
                synchronized(GithubUserDB::class){
                    val dbName = "github_database"
                    INSTANCE = Room.databaseBuilder(context.applicationContext, GithubUserDB::class.java, dbName).build()
                }
            }else{
            }
            return INSTANCE
        }
    }
    abstract fun favoriteUserInterfaceQueries():FavoriteUserInterfaceQueries
}