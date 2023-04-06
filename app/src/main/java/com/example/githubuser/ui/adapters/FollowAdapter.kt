package com.example.githubuser.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.FollowResponseItem
import com.example.githubuser.R

class FollowAdapter(private var listFollow : List<FollowResponseItem>) : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {

    class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUser: TextView = itemView.findViewById(R.id.tv_users)
        val ivUser: ImageView = itemView.findViewById(R.id.iv_users)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)
        return FollowViewHolder(view)
    }

    override fun getItemCount(): Int = listFollow.size

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        val users = listFollow[position]
        holder.tvUser.text = users.login
        Glide.with(holder.ivUser)
            .load(users.avatarUrl)
            .into(holder.ivUser)
    }
    fun updateData(newFollowList: List<FollowResponseItem>){
        listFollow = newFollowList
        notifyDataSetChanged()
    }
}
