package com.example.githubuser.api

import com.example.githubuser.FollowResponseItem
import com.example.githubuser.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUsers(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<UserResponse>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<FollowResponseItem>>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<FollowResponseItem>>
}
