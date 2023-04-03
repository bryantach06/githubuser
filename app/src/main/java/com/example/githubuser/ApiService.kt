package com.example.githubuser

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
    fun getUserFollowing(@Path("username") username: String): Call<List<FollowResponse>>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<FollowResponse>
}
