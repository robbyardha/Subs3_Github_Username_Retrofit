package com.ardhacodes.favorite_github

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardhacodes.favorite_github.databinding.ItemGithubBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class GithubuserAdapter: RecyclerView.Adapter<GithubuserAdapter.UserViewHolder>() {
    private val list = ArrayList<Githubuser>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback)
    {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users : ArrayList<Githubuser>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemGithubBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: Githubuser)
        {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            var bindItemUsername = binding.tvItemUsername
            var bindItemId = binding.tvItemId
            var bindItemType = binding.tvItemType

            bindItemUsername.text = user.login
            bindItemId.text = user.id.toString()
            bindItemType.text = user.type
            Glide.with(itemView)
                .load(user.avatar_url)
                .into(binding.avatar)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder  {
        val view = ItemGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnItemClickCallback{
        fun onItemClicked(data: Githubuser)
    }
}