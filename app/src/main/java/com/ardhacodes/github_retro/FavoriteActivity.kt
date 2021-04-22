package com.ardhacodes.github_retro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        TitleActionBar()
        getviewModelIntents()
        configRvList()

    }

    private fun getviewModelIntents() {
        viewModel = ViewModelProvider(this).get(FavoriteVM::class.java)
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Githubuser) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatar_url)
                intent.putExtra(DetailUserActivity.EXTRA_TYPE, data.type)
                startActivity(intent)
            }
        })
    }

    private fun configRvList()
    {
        //LayoutManager
        binding.rvUserFav.setHasFixedSize(true)
        binding.rvUserFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        binding.rvUserFav.adapter = adapter


        viewModel.getFavoriteUser()?.observe(this,{
            if (it!=null){
                val list = ListofMapping(it)
                adapter.setList(list)
                showLoading(false)
            }else{
                showLoading(true)
            }
        })
    }

    private fun ListofMapping(users: List<FavoriteUserEntity>): ArrayList<Githubuser> {
        val listuser = ArrayList<Githubuser>()
        for (user in users){
            val githubuserMappedIn = Githubuser(
                user.login,
                user.id,
                user.avatar_url,
                user.type
            )
            listuser.add(githubuserMappedIn)
            showLoading(false)
        }
        return listuser
    }

    private fun TitleActionBar()
    {
        val action = supportActionBar
        action?.title = "My Favorite Github Username"
    }

    private fun showLoading(state: Boolean)
    {
        if (state){
            binding.progressBar2.visibility = View.VISIBLE
        }else{
            binding.progressBar2.visibility = View.GONE
        }
    }
}