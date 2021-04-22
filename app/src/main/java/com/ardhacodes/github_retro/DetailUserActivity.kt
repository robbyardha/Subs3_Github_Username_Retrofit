package com.ardhacodes.github_retro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.ardhacodes.github_retro.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME =  "extra_username"
        const val EXTRA_ID =  "extra_id"
        const val EXTRA_AVATAR_URL =  "extra_avatar_url"
        const val EXTRA_TYPE =  "extra_type"
    }

    private lateinit var binding: ActivityDetailUserBinding

    private lateinit var viewModel: DetailUserViewModel
    var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailGithub()
        configurationOfViewPager()
    }

    private fun detailGithub()
    {
        //IntentStringExtra
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatar_url = intent.getStringExtra(EXTRA_AVATAR_URL)
        val type = intent.getStringExtra(EXTRA_TYPE)
        //bundle fragment
        bundle.putString(EXTRA_USERNAME, username)
//        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        if (username == null){

        }else{
            viewModel.setUserDetail(username)
        }
//        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this, {
            if (it != null){
                //Declaration Binding get XML
                val bindLogin = binding.tvUsername
                val bindNama = binding.tvNama
                val bindBio = binding.tvBio
                val bindCompany = binding.tvCompany
                val bindLocation = binding.tvLocation
                val bindFollowers= binding.tvFollowers
                val bindFollowings = binding.tvFollowing
                val bindRepository = binding.tvRepository
                val bindAvatarGithub = binding.circleAvatarDetail

//                Load data
                bindLogin.text = it.login
                bindNama.text = it.name
                bindBio.text = it.bio
                bindCompany.text = "Company : ${it.company}"
                bindLocation.text = "Location : ${it.location}"
                bindFollowers.text = "Followers: ${it.followers}"
                bindFollowings.text = "Following: ${it.following}"
                bindRepository.text ="Repository: ${it.public_repos}"
                Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .into(bindAvatarGithub)

                //LoadAction Bar name


                //Toggle
                var isChecked = false
                CoroutineScope(Dispatchers.IO).launch {
                    val count = viewModel.checkUser(id)
                    withContext(Dispatchers.Main){
                        if(count != null){
                            if (count > 0){
                                binding.toggleFavorite.isChecked = true
                                isChecked = true
                            }else{
                                binding.toggleFavorite.isChecked = false
                                isChecked = false
                            }
                        }
                    }
                }

                binding.toggleFavorite.setOnClickListener{
                    isChecked = !isChecked
                    if (isChecked){
                        if (avatar_url != null) {
                            if (username != null) {
                                if (type != null) {
                                    viewModel.addFavorite(username, id, avatar_url, type)
                                }
                            }
                        }
                    }else{
                        viewModel.removeFromFavorite(id)
                    }
                    binding.toggleFavorite.isChecked = isChecked
                }

            }
        })
    }

    private fun configurationOfViewPager()
    {
        var sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.viewPager.adapter = sectionPagerAdapter
        var bindClickTabs = binding.clicktabs
        bindClickTabs.setupWithViewPager(binding.viewPager)
    }

    fun TitleActionBar()
    {
        val username = intent.getStringExtra(EXTRA_USERNAME)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        var actionBarTitle = supportActionBar
        actionBarTitle!!.title = "Detail Of ${username}"
    }
    private fun toggleCheck()
    {

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