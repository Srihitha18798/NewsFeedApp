package com.example.newsfeedapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeedapp.Adapter.ViewHolder.ListNewsAdapter
import com.example.newsfeedapp.Common.APIService
import com.example.newsfeedapp.Interface.NewsService
import com.example.newsfeedapp.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {


    var source=""
    var webHotUrl:String?=""

    lateinit var dialog: AlertDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        //init view
        mService= APIService.newsService

        dialog = SpotsDialog.Builder().setContext(this).build()

        swipe_to_refresh.setOnRefreshListener{loadNews(source,true)}

        diagonalLayout.setOnClickListener{
            val detail= Intent(baseContext,NewsDetail::class.java)
            detail.putExtra("webURL",webHotUrl)
            startActivity(detail)
        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager= LinearLayoutManager(this)

        if(intent!=null)
        {
            source= intent.getStringExtra("source").toString()
            if(!source.isEmpty())
                loadNews(source,false)

        }


    }

    private fun loadNews(source: String?,isRefreshed:Boolean) {
        if(isRefreshed)
        {
            dialog.show()
            mService.getNewsFromSource (APIService.getNewsAPI(source!!))
                .enqueue(object : Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        TODO("Not yet implemented")
                    }


                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        dialog.dismiss()
                       //  swipe_to_refresh.isRefreshing=false

                        //get first article to HotNews
                        Picasso.with(baseContext)
                            .load(response!!.body()!!.articles!![0].urlToImage)
                            .into(top_image)
                        top_title.text=response!!.body()!!.articles!![0].title
                        top_author.text=response!!.body()!!.articles!![0].author
                        webHotUrl=response!!.body()!!.articles!![0].url

                        //load remaining articles

                        val removeFirstItem=response!!.body()!!.articles


                        removeFirstItem!!.removeAt(0)
                        adapter= ListNewsAdapter(removeFirstItem!!,baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter=adapter
                    }
                })
        }
        else
        {
//            dialog.show()
            swipe_to_refresh.isRefreshing=true
            mService.getNewsFromSource(APIService.getNewsAPI(source!!))
                .enqueue(object : Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {

                        swipe_to_refresh.isRefreshing=false

                        //get first article to HotNews
                        Picasso.with(baseContext)
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(top_image)
                        top_title.text= response.body()!!.articles!![0].title
                        top_author.text= response.body()!!.articles!![0].author
                        webHotUrl= response.body()!!.articles!![0].url

                        //load remaining articles

                        val removeFirstItem= response.body()!!.articles


                        removeFirstItem!!.removeAt(0)
                        adapter= ListNewsAdapter(removeFirstItem,baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter=adapter
                    }
                })
        }

    }
}