package com.example.jwstimager;

import com.example.jwstimager.model.Feed;


import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedAPI {
    String BASE_URL = "https://www.reddit.com/r/";
    @GET("jameswebb/.rss")
    Call<Feed> getFeed();
}