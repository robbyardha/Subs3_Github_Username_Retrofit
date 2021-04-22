package com.ardhacodes.github_retro

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application){
    val user = MutableLiveData<DetailGithubResponse>()

    //init user dao interface
    private var userDaoFavInterfaceFavInterface: FavoriteUserInterfaceQueries?
    private var userDb: GithubUserDB?

    //inisialisasi
    init {
        userDb = GithubUserDB.getDatabase(application)
        userDaoFavInterfaceFavInterface = userDb?.favoriteUserInterfaceQueries()
    }
    //setter
    fun setUserDetail(username: String) {
        BaseUrlClient.baseUrlApi
            .getUserDetail(username)
            .enqueue(object : Callback<DetailGithubResponse>{
                override fun onResponse(call: Call<DetailGithubResponse>, response: Response<DetailGithubResponse>) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailGithubResponse>, t: Throwable) {
                    t.message?.let { Log.d("FAILURE", it) }
                }

            })
    }

    //getter
    fun getUserDetail() : LiveData<DetailGithubResponse>
    {
        return user
    }

    //Func add favorite
    fun addFavorite(username: String, id: Int, avatar_url: String, type:String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUserEntity(
                username,
                id,
                avatar_url,
                type
            )
            userDaoFavInterfaceFavInterface?.InsertToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDaoFavInterfaceFavInterface?.getWhereUser(id)
    fun removeFromFavorite(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDaoFavInterfaceFavInterface?.DeleteFromFavorite(id)
        }
    }
}