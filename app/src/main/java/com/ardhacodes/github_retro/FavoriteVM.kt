package com.ardhacodes.github_retro

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavoriteVM(application: Application): AndroidViewModel(application) {
    private var favinterfacequeries: FavoriteUserInterfaceQueries?
    private var githubUserDb: GithubUserDB?

    //initialization
    init {
        githubUserDb = GithubUserDB.getDatabase(application)
        favinterfacequeries = githubUserDb?.favoriteUserInterfaceQueries()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUserEntity>>? {
        return favinterfacequeries?.getAllFavoriteUser()
    }
}