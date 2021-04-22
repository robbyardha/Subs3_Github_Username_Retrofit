package com.ardhacodes.favorite_github

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.favorite_github.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : GithubuserAdapter
    private lateinit var viewModel: FavoriteVM
    private var arrList: ArrayList<Githubuser> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        TitleActionbar()
        configRvFav()
    }

    private fun configRvFav()
    {
        adapter = GithubuserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteVM::class.java)


        binding.rvUserFav.setHasFixedSize(true)
        binding.rvUserFav.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvUserFav.adapter = adapter


        viewModel.setFavoriteUser(this)
        viewModel.getFavoriteUser()?.observe(this,{
            if (it!=null){
                adapter.setList(it)
            }
        })
    }

    private fun TitleActionbar()
    {
        val actionBar = supportActionBar
        actionBar?.title = "Favorite Username Github"
    }
}