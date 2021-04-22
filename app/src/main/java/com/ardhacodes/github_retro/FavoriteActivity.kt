package com.ardhacodes.github_retro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.github_retro.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter : UserAdapter
    private lateinit var viewModel: FavoriteVM
    private var arrList: ArrayList<Githubuser> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter(arrList)
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteVM::class.java)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Githubuser) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    //Add Id Utk dikirim
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatar_url)
                    it.putExtra(DetailUserActivity.EXTRA_TYPE, data.type)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUserFav.setHasFixedSize(true)
            rvUserFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFav.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this,{
            if (it!=null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavoriteUserEntity>): ArrayList<Githubuser> {
        val listuser = ArrayList<Githubuser>()
        for (user in users){
            val userMapped = Githubuser(
                user.login,
                user.id,
                user.avatar_url,
                user.type
            )
            listuser.add(userMapped)
        }
        return listuser
    }
}