package com.ardhacodes.github_retro

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavoriteVM(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUserInterfaceQueries?
    private var userDb: GithubUserDB?

    //inisialisasi
    init {
        userDb = GithubUserDB.getDatabase(application)
        userDao = userDb?.favoriteUserInterfaceQueries()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>>? {
        return userDao?.getFavoriteUser()
    }
}