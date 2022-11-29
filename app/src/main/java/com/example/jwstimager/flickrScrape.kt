package com.example.jwstimager

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.concurrent.thread

class flickrScrape {

    //ArrayList of urls, use this variable to access the links
    var urls: ArrayList<String> = arrayListOf()

    //use this function to update the urls array list
    fun scrape() {
        thread{
        val url = URL("https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=deb1c7b6adff4c7145b39a1d49f50f1b&photoset_id=72177720301006030&user_id=50785054%40N03&extras=url_o&format=rest")
        val connection = url.openConnection()
        BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
            var line: String?
            while (inp.readLine().also { line = it } != null) {
                //println(line)
                var g = line?.split("url_o=\"")
                if (g != null) {
                    if(g.size > 1){
                        var h = g[1].split("\" height")
                        urls?.add(h[0])
                    }
                }
            }
        }
    }
    }

    fun getURLs(): ArrayList<String> {
        return urls
    }


}