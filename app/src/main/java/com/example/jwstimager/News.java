package com.example.jwstimager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jwstimager.model.Feed;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class News extends AppCompatActivity {
    private static final String TAG = "News";
    private static final String BASE_URL = "https://www.reddit.com/r/";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        FeedAPI feedAPI = retrofit.create(FeedAPI.class);

        Call<Feed> call = feedAPI.getFeed();

        call.enqueue(new Callback<Feed>() {
            //On success
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                Log.d(TAG, "onResponse: feed: " + response.body().toString());
                Log.d(TAG, "onResponse: Server Response: " + response.toString());
            }
            //On failure
            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS" + t.getMessage());
                Toast.makeText(News.this, "An Error Occured", Toast.LENGTH_SHORT).show();
            }
        });
    }

}