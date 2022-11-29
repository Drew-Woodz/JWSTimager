package com.example.jwstimager

//import androidx.compose.foundation.layout.BoxScopeInstance.align

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import java.io.File
import kotlin.concurrent.thread
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException
//import java.util.ArrayList
import kotlin.collections.ArrayList
//import java.io.*

class MainActivity : ComponentActivity() {
    val posts = ArrayList<Post>()
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
                        Toast.makeText(this@MainActivity, "An Error Occured", Toast.LENGTH_SHORT)
                            .show()
                    }
                })


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(posts)
                }
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
fun NewsCard(post : Post){

    Box(Modifier.fillMaxWidth()){
        Column(){
            Surface(modifier = Modifier
                .height(100.dp)
                .padding(3.dp)
                .fillMaxWidth()
                .border(width = 1.dp,color = Color(0x8899abC8) ),
                color = MaterialTheme.colorScheme.primary
            ) {
                Column(modifier = Modifier
                    .border(width = 1.dp,color = Color(0x8899abC8) )
                ){
                    Row(){
                        Column(modifier = Modifier
                            .height(35.dp)
                            .width(35.dp)
                            .padding(1.dp)
                            .border(width = 1.dp,color = Color(0x8899abC8) )
                        ){
                            //thumbnail
                            AsyncImage(model = post.thumbnailURL,
                                contentDescription = post.title,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                        Column(){
                            //title <-link
                            Text(
                                text = post.title,
                                color = Color(0xFFFFFFFF)

                            )
                        }
                    }
                    Row(){
                        //content
                        Text(

                            text = post.author
                        )
                    }
                    Row(){
                        //date
                        Text(
                        text = post.date_updated
                        )
                    }


                }
            }
        }
    }
}



//
//

class Favorite : ViewModel()
{
    private var isFavorite : MutableLiveData<Boolean> = MutableLiveData(false)
    var favorite : MutableLiveData<Boolean> = isFavorite
    //var favorite by rememberSaveable { MutableState(false) }
    var myFavorite = { mutableStateOf(false) }


}


/**
@Composable
fun FavoriteBoleean (){
    var isFavorite : Boolean by remember { mutableStateOf(false) }
    ImageCard(isFavorite = isFavorite, onClickChange = {isFavorite = it})
}
*/

@SuppressLint("RestrictedApi")
@Composable
fun ImageCard(imageurl: String) {

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()
        ){ isGranted: Boolean ->
            if(isGranted){
                Log.i("Permission: ", "Granted")
            }
            else {
                Log.i("Permission: ", "Denied")
            }

        }
    val context = LocalContext.current

    lateinit var viewModel: Favorite
    val coroutineScope = rememberCoroutineScope()
    var expandMenu by remember { mutableStateOf(false) }
    var isFavorite by rememberSaveable { mutableStateOf(false) }
    // keep track of whether or not the image card is expanded
    var isExpanded by remember { mutableStateOf(false) }
    // surfaceColor will be updated gradually from one color to the other
    Box(
        modifier = Modifier
            .animateContentSize()
            .padding(all = 8.dp)
    ) {
        AsyncImage(model = imageurl,
            contentDescription = "A JWST image",
            modifier = Modifier
                //.size(300.dp)
                //.border(1.5.dp, MaterialTheme.colorScheme.primary)
                //toggle is expanded by clicking on the image
                .clickable { isExpanded = !isExpanded }


        )


        /*****************
         *  Start Buttons  *
         ******************/

        Box(Modifier
            .fillMaxSize()
            .padding(5.dp, 0.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(onClick = {
                expandMenu = !expandMenu
            }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "More options button",
                    tint = Color.LightGray,
                    modifier = Modifier.size(30.dp)
                )
            }

            AnimatedVisibility(visible = expandMenu) {

                /*****************
                 *  Share Button  *
                 ******************/
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(0.dp, 40.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(
                        onClick = {

                            // Get image from url
                            val imageLoader = ImageLoader(context)
                            val request = ImageRequest.Builder(context)
                                .data(imageurl)
                                .build()

                            coroutineScope.launch {
                                val drawable = imageLoader.execute(request).drawable

                                //Get Bitmap from imageView
                                val bitmap = drawable?.toBitmap() // your imageView here.

                                //Compress image
                                val bytes = ByteArrayOutputStream()
                                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

                                //Save image & get path of it
                                val path =
                                    MediaStore.Images.Media.insertImage(context.contentResolver,
                                        bitmap,
                                        "tempimage",
                                        null)

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


                /*****************
                 * Favorite Button *
                 ******************/
                //var isFavorite: LiveData<Boolean> = viewModel.favorite

                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(0.dp, 80.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconToggleButton( checked = isFavorite,
                        onCheckedChange = {
                            isFavorite = !isFavorite
                        }
                    ){
                        val favIcon: ImageVector

                        if (isFavorite) {
                            favIcon = Icons.Filled.Favorite
                            //image.isFavorite = true
                        }
                        else {
                            favIcon = Icons.Rounded.FavoriteBorder
                            //image.isFavorite = false
                        }

                        Icon(
                            imageVector = favIcon,
                            contentDescription = "Favorite Icon",
                            tint = Color.LightGray,
                            modifier = Modifier.size(25.dp)
                        )

                    }

                }

                /*****************
                 * Download Button *
                 ******************/
                Box(Modifier
                    .fillMaxSize()
                    .padding(0.dp, 120.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    IconButton(
                        onClick = {
                            val directory = File(Environment.DIRECTORY_DOWNLOADS)

                            if(!directory.exists()){
                                directory.mkdirs()
                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                downloadImage(imageurl, directory, context, "JWSTimager-image")
                                Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show()

                                when {
                                    (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) -> {
                                            //TODO
                                        //downloadImage(image.src_link, directory, context, image.title)
                                        //Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show()

                                    }
                                    else -> {
                                        //PERMISSION HAS NOT BEEN ASKED YET
                                        requestPermissionLauncher.launch(
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        )

                                    }
                                }
                            }
                            else {
                                    downloadImage(imageurl, directory, context, "JWSTimager-image")
                                    Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show()
                                }

                        })
                    {
                        Icon(
                            imageVector = Icons.Outlined.ArrowDropDown,
                            contentDescription = "Download Button",
                            tint = Color.LightGray,
                            modifier = Modifier.size(35.dp)
                        )
                    }

                }
            }
        }

        // End Buttons***************************************************************
    }
}

@SuppressLint("Range")
private fun downloadImage(url: String, directory: File, context: Context, title: String) {

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    val downloadUri = Uri.parse(url)

    val request = DownloadManager.Request(downloadUri).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setTitle(title)
            .setDescription("JWSTimager")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                directory.toString(),
                url.substring(url.lastIndexOf("/") + 1)
            )
    }

    val downloadId = downloadManager.enqueue(request)
    val query = DownloadManager.Query().setFilterById(downloadId)

    val cursor: Cursor = downloadManager.query(query)
    cursor.moveToFirst()
    val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

    var outcome = " "
    outcome = when (status)
    {
        DownloadManager.STATUS_FAILED -> "Download has been failed, please try again"
        DownloadManager.STATUS_RUNNING -> "Downloading"
        DownloadManager.STATUS_SUCCESSFUL -> "Image downloaded successfully"

    else-> "No Image Found"

    }
    //showToast(outcome, context)
}


private fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
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
            //val context = LocalContext.current
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
fun ScrollingImageList(imageList: ArrayList<String>) {
    LazyColumn {
        items(imageList) { imageurl ->
            ImageCard(imageurl)
        }
    }
}


//
//
@Composable
fun ScrollingNewsList(posts: ArrayList<Post>) {
    LazyColumn {
        items(posts) { post ->
            NewsCard(post)
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
