package com.example.jwstimager

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.jwstimager.model.Feed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException

//private const val TAG = "News"
//private const val BASE_URL = "https://www.reddit.com/r/"

class RedditScraper {


    fun scrape(posts: ArrayList<Post>, context: Context) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
        val feedAPI = retrofit.create(FeedAPI::class.java)
        val call = feedAPI.feed


        call.enqueue(object : Callback<Feed> {
            //On success
            override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
                //Log.d(TAG, "onResponse: feed: " + response.body().toString());
                Log.d(TAG, "onResponse: Server Response: $response")
                val entrys = response.body()!!
                    .entrys
                Log.d(TAG, "onResponse: entrys: " + response.body().toString())
                Log.d(TAG, "onResponse: author: " + entrys[0].author.name)
                Log.d(TAG, "onResponse: updated: " + entrys[0].updated)
                Log.d(TAG, "onResponse: title: " + entrys[0].title)
                for (i in entrys.indices) {
                    val extractXML1 = ExtractXML(entrys[i].content, "<a href=")
                    val postContent = extractXML1.start()
                    val extractXML2 = ExtractXML(entrys[i].content, "<img src=")
                    try {
                        postContent.add(extractXML2.start()[0])
                    } catch (e: NullPointerException) {
                        postContent.add(null)
                        Log.e(TAG,
                            "onResponse: NullPointerException(thumbnail):" + e.message)
                    } catch (e: IndexOutOfBoundsException) {
                        postContent.add(null)
                        Log.e(TAG,
                            "onResponse: IndexOutOfBoundsException(thumbnail):" + e.message)
                    }

                    //var postList = mutableListOf<Post>()
                    val lastPosition = postContent.size - 1
                    posts.add(
                        Post(
                            entrys[i].title,
                            entrys[i].author.name,
                            entrys[i].updated,
                            postContent[0],
                            postContent[lastPosition]
                        )
                    )
                }
                for (j in posts.indices) {
                    Log.d(
                        TAG, """onResponse: 
                        PostURL: ${posts[j].postURL}
                        ThumbnailURL: ${posts[j].thumbnailURL}
                        Title: ${posts[j].title}
                        Author: ${posts[j].author}
                        updated: ${posts[j].date_updated}
                        """
                    )

                }
            }

            //On failure
            override fun onFailure(call: Call<Feed>, t: Throwable) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS" + t.message)
                Toast.makeText(context, "An Error Occured", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }

    companion object {
        private const val TAG = "News"
        private const val BASE_URL = "https://www.reddit.com/r/"
    }


}
