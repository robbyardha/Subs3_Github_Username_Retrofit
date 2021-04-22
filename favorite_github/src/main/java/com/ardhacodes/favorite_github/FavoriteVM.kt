package com.ardhacodes.favorite_github

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteVM(application: Application) : AndroidViewModel(application)  {
    private var arrList = MutableLiveData<ArrayList<Githubuser>>()

    //Setter Favorite User
    fun setFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query(BaseColumnDB.FavoriteUserColumn.CONTENT_URI, null, null, null, null)
        val listConverted = Helper.mapCursorToArrayList(cursor)
        arrList.postValue(listConverted)
    }
    //Getter Favorite User
    fun getFavoriteUser(): LiveData<ArrayList<Githubuser>> {
        return arrList
    }
}