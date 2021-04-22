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

//        configRvFav()
//        adapter = GithubuserAdapter(arrList)
        adapter = GithubuserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteVM::class.java)

//        binding.apply {
//            rvUserFav.setHasFixedSize(true)
//            rvUserFav.layoutManager = LinearLayoutManager(this@MainActivity)
//            rvUserFav.adapter = adapter
//        }
        binding.rvUserFav.setHasFixedSize(true)
        binding.rvUserFav.layoutManager = LinearLayoutManager(this)
        binding.rvUserFav.adapter = adapter

        viewModel.setFavoriteUser(this)
        viewModel.getFavoriteUser()?.observe(this,{
            if (it!=null){
                adapter.setList(it)
            }
        })

    }

    private fun configRvFav()
    {
//        adapter = GithubuserAdapter(arrList)
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteVM::class.java)

//        binding.apply {
//            rvUserFav.setHasFixedSize(true)
//            rvUserFav.layoutManager = LinearLayoutManager(this@MainActivity)
//            rvUserFav.adapter = adapter
//        }
//        binding.rvUserFav.setHasFixedSize(true)
//        binding.rvUserFav.layoutManager = LinearLayoutManager(this)
//        binding.rvUserFav.adapter = adapter

        viewModel.setFavoriteUser(this)
        viewModel.getFavoriteUser()?.observe(this,{
            if (it!=null){
                adapter.setList(it)
            }
        })
    }
}