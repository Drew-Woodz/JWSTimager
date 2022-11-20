package com.example.jwstimager

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jwstimager.model.Feed
import com.example.jwstimager.ui.theme.JWSTimagerTheme
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory.*
import java.io.ByteArrayOutputStream
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException
import java.util.ArrayList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JWSTimagerTheme {
                // A surface container using the 'background' color from the theme

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(create())
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
                    val posts = ArrayList<Post>()
                    for (i in entrys.indices) {
                        val extractXML1 = ExtractXML(entrys[i].content, "<a href=")
                        val postContent = extractXML1.start()
                        val extractXML2 = ExtractXML(entrys[i].content, "<img src=")
                        try {
                            postContent.add(extractXML2.start()[0])
                        } catch (e: NullPointerException) {
                            postContent.add(null)
                            Log.e(TAG, "onResponse: NullPointerException(thumbnail):" + e.message)
                        } catch (e: IndexOutOfBoundsException) {
                            postContent.add(null)
                            Log.e(TAG, "onResponse: IndexOutOfBoundsException(thumbnail):" + e.message)
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
                    Toast.makeText(this@MainActivity, "An Error Occured", Toast.LENGTH_SHORT).show()
                }
            })
        }

            Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }

        }


    }
    companion object {
        private const val TAG = "News"
        private const val BASE_URL = "https://www.reddit.com/r/"
    }
}

//
//
@Composable
fun NewsCard(posts: ArrayList<Post>){

    Box(Modifier.fillMaxWidth()){
        Column(){
            Surface(modifier = Modifier
                .height(100.dp)
                .padding(3.dp)
                .fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            ) {
                Column(){
                    Row(){
                        Column(){
                            //thumbnail
                            AsyncImage(model = posts.thumbnailURL,
                                contentDescription = posts.title,
                                modifier = Modifier
                            )
                        }
                        Column(){
                            //title <-link
                            Text(
                                text = posts.title
                            )
                        }
                    }
                    Row(){
                        //content
                        Text(
                            text = posts.author
                        )
                    }
                    Row(){
                        //date
                        Text(
                        text = posts.date_updated
                        )
                    }


                }
            }
        }
    }
}



//
//
@Composable
fun ImageCard(image: ImageData) {

    val coroutineScope = rememberCoroutineScope()
    var isFavorite by remember { mutableStateOf(false) }
    // keep track of whether or not the image card is expanded
    var isExpanded by remember { mutableStateOf(false) }
    // surfaceColor will be updated gradually from one color to the other
    Box(
        modifier = Modifier
            .animateContentSize()
            .padding(all = 8.dp)
    ) {
        AsyncImage(model = image.src_link,
            contentDescription = image.title,
            modifier = Modifier
                //.size(300.dp)
                //.border(1.5.dp, MaterialTheme.colorScheme.primary)
                //toggle is expanded by clicking on the image
                .clickable { isExpanded = !isExpanded }
        )

        Box(
            Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            val context = LocalContext.current
            IconButton(
                onClick = {
                    // Get image from url
                    val imageLoader = ImageLoader(context)
                    val request = ImageRequest.Builder(context)
                        .data(image.src_link)
                        .build()

                    coroutineScope.launch {
                        val drawable = imageLoader.execute(request).drawable

                        //Get Bitmap from imageView
                        val bitmap = drawable?.toBitmap() // your imageView here.

                        //Compress image
                        val bytes = ByteArrayOutputStream()
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

                        //Save image & get path of it
                        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "tempimage", null)

                        //Get URI of image
                        val uri = Uri.parse(path)

                        // activity to share image with Intent
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "From JWST Imager App")
                            type = "image/png"
                            putExtra(Intent.EXTRA_STREAM, uri)
                        }

                        context.startActivity(shareIntent)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Box(
            Modifier
                .fillMaxSize()
                .padding(12.dp)
                .offset(y = 60.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            val context = LocalContext.current
            IconButton(
                onClick = { isFavorite = !isFavorite }
            ) {

                var favIcon: ImageVector
                if (isFavorite) {
                    favIcon = Icons.Filled.Check
                    image.isFavorite = true
                }
                else {
                    favIcon = Icons.Rounded.Add
                    image.isFavorite = false
                }
                Icon(
                    imageVector = favIcon,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(25.dp)
                )
            }
        }


            AnimatedVisibility(visible = isExpanded) {
                //Spacer(modifier = Modifier.width(8.dp))

                Box (
                    Modifier
                        .wrapContentWidth()
                        .padding(all = 4.dp)
                ){
                    Text(
                        text = image.title,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
}

//
//
@Composable
fun GalleryImageCard(image: ImageData) {

    var isSelected by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .animateContentSize()
            .padding(all = 8.dp)
    ) {
        AsyncImage(model = image.src_link,
            contentDescription = image.title,
            modifier = Modifier
                //.size(300.dp)
                //.border(1.5.dp, MaterialTheme.colorScheme.primary)
                //toggle is expanded by clicking on the image
                .clickable { isSelected = !isSelected }
        )
        Box(
            Modifier
                .fillMaxSize()
                .padding(12.dp)
                .offset(y = 60.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            val context = LocalContext.current
            IconButton(
                onClick = { isSelected = !isSelected }
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(25.dp)
                    )
                }

            }
        }
    }
}




///* ------------------------------Scrolling Lists-------------------------------------- *///
//
//
@Composable
fun ScrollingImageList(imageList: List<ImageData>) {
    LazyColumn {
        items(imageList) { image ->
            ImageCard(image)
        }
    }
}


//
//
@Composable
fun ScrollingNewsList(posts: ArrayList<Post>) {
    LazyColumn {
        items(posts) { posts ->
            NewsCard(posts)
        }
    }
}



//
//
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ScrollingGridList(imageList: List<ImageData>) {

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        content = {
            items(imageList) { image ->
                GalleryImageCard(image)
            }
        }
    )

}
