package com.example.githubuser.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.ItemsItem
import com.example.githubuser.R
import com.example.githubuser.api.UserResponse
import com.example.githubuser.ui.activities.DetailUserActivity

class UsersAdapter(private var listUsers: List<ItemsItem>): RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    companion object {
        const val EXTRA_USER = "user"
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        MyViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_users, viewGroup, false))

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val user = listUsers[position]
        myViewHolder.tvUser.text = user.login
        Glide.with(myViewHolder.ivUser)
            .load(listUsers[position].avatarUrl)
            .into(myViewHolder.ivUser)

        myViewHolder.itemView.setOnClickListener {
            val intent = Intent(myViewHolder.itemView.context, DetailUserActivity::class.java)
            val userResponse = UserResponse(
                gistsUrl = "",
                reposUrl = "",
                followingUrl = "",
                createdAt = "",
                login = user.login,
                type = "",
                blog = "",
                subscriptionsUrl = "",
                updatedAt = "",
                siteAdmin = false,
                company = "",
                id = user.id,
                publicRepos = 0,
                gravatarId = "",
                organizationsUrl = "",
                starredUrl = "",
                followersUrl = "",
                publicGists = 0,
                url = "",
                receivedEventsUrl = "",
                followers = 0,
                avatarUrl = user.avatarUrl,
                eventsUrl = "",
                htmlUrl = "",
                following = 0,
                name = "",
                location = "",
                nodeId = ""
            )
            intent.putExtra(EXTRA_USER, userResponse)
            intent.putExtra("login", userResponse.login)
            intent.putExtra("photo", userResponse.avatarUrl)
            intent.putExtra("name", userResponse.name)
            myViewHolder.itemView.context.startActivity(intent)
            }
        }

    override fun getItemCount() = listUsers.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUser: TextView = view.findViewById(R.id.tv_users)
        val ivUser: ImageView = view.findViewById(R.id.iv_users)
    }
}
