package com.example.newsfeedapp.Interface


import com.example.newsfeedapp.Model.News
import com.example.newsfeedapp.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {
    @get:GET("v2/sources?apiKey=0efc20295726490eab55baee42025686")
    val sources: Call<WebSite>

    // we have used suspend because we are using coroutines and just run asynchronous with main
    // thread and does not stuck any process or thread
    @GET
    fun getNewsFromSource(@Url url: String): Call<News>
}