package com.ardhacodes.github_retro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardhacodes.github_retro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private var arrList : ArrayList<Githubuser> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        TitleActionBar()
        selectedUsernameGithub()


        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        binding.rvGithubuser.layoutManager = LinearLayoutManager(this)
        binding.rvGithubuser.setHasFixedSize(true)
        binding.rvGithubuser.adapter = adapter

        //GetUsers
        binding.btnSearch.setOnClickListener{
            searchUsers()
        }

        binding.editQuery.setOnKeyListener { v, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                searchUsers()
                return@setOnKeyListener true
            }else if(event.action == null && keyCode == null){
                GetUsers()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        viewModel.getSearchUsers().observe(this)
        {
         if (it!=null){
             adapter.setList(it)
             showLoading(false)
            }else{
         }
        }
    }

    private fun searchUsers()
    {
        val query = binding.editQuery.text.toString()
        if (query.isEmpty() || query == null) return
        showLoading(true)
        viewModel.setSearchUsername(query)

    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun GetUsers()
    {
        binding.apply {
            showLoading(false)
            viewModel.setUsers()
        }
    }

    private fun TitleActionBar()
    {
        var ActionBarTitleMain = supportActionBar
        ActionBarTitleMain?.title = "Github Username API"
    }

    private fun selectedUsernameGithub()
    {
        adapter = UserAdapter(arrList)
        adapter.notifyDataSetChanged()

//        adapter = UserAdapter()
//        adapter.notifyDataSetChanged()
//
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Githubuser) {

                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intent.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, data.avatar_url)
                intent.putExtra(DetailUserActivity.EXTRA_TYPE, data.type)
                startActivity(intent)

//                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
//                var intentPutExtra = intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
//
//                startActivity(intentPutExtra)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        TitleActionBar()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.fav_menu -> {
                val intentFav = Intent(this, FavoriteActivity::class.java)
                startActivity(intentFav)
            }
            R.id.setting_menu -> {
                val intentSettings = Intent(this, SettingActivity::class.java)
                startActivity(intentSettings)

            }
        }
        return super.onOptionsItemSelected(item)
    }

}