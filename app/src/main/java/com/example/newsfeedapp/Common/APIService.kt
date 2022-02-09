package com.example.newsfeedapp.Common

import com.example.newsfeedapp.Interface.NewsService
import com.example.newsfeedapp.Remote.RetrofitClient
import java.lang.StringBuilder

object APIService {
    val BASE_URL = "https://newsapi.org/";
    val API_KEY = "0efc20295726490eab55baee42025686"


    val newsService: NewsService
        get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source: String): String {
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl;

    }


}
