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
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.jwstimager.ui.theme.JWSTimagerTheme
import kotlinx.coroutines.launch
import java.io.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JWSTimagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
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
fun ImageCard(image: ImageData) {

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
        AsyncImage(model = image.src_link,
            contentDescription = image.title,
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
                            image.isFavorite = true
                        }
                        else {
                            favIcon = Icons.Rounded.FavoriteBorder
                            image.isFavorite = false
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
                                downloadImage(image.src_link, directory, context, image.title)
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
                                    downloadImage(image.src_link, directory, context, image.title)
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

data class AboutData(
    val name: String,
    val aboutText: String,
    val email: String,
    val linkedin: String,
)


@Composable
fun AboutCard(aboutEntry: AboutData) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .border(1.5.dp, MaterialTheme.colorScheme.primary)
    )
    {
        Column() {
            Text(text = aboutEntry.name,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(10.dp))
            Text(text = aboutEntry.aboutText,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(10.dp))
            Text(text = aboutEntry.email,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(10.dp))
            Text(text = aboutEntry.linkedin,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(10.dp))
        }
    }
}


@Composable
fun AboutPage() {

    val aboutEntryList = listOf<AboutData>(
        AboutData(
            name = stringResource(R.string.nameAndrew),
            aboutText = stringResource(R.string.about_andrew),
            email = stringResource(R.string.andrew_email),
            linkedin = stringResource(R.string.andrew_linkedin),
        ),
        AboutData(
            name = stringResource(R.string.nameDeclan),
            aboutText = stringResource(R.string.about_declan),
            email = stringResource(R.string.declan_email),
            linkedin = stringResource(R.string.declan_linkedin),
        ),
        AboutData(
            name = stringResource(R.string.nameEmmanuel),
            aboutText = stringResource(R.string.about_emmanuel),
            email = stringResource(R.string.emmanuel_email),
            linkedin = stringResource(R.string.emmanuel_linkedin),
        ),

        AboutData(
            name = stringResource(R.string.nameJadrien),
            aboutText = stringResource(R.string.about_jadrien),
            email = stringResource(R.string.jadrien_email),
            linkedin = stringResource(R.string.jadrien_linkedin),
        )
    )
    Column() {
        Text(
            text = "About Us",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally)

        )
        LazyColumn {
            items(aboutEntryList) { entry ->
                AboutCard(entry)
            }

        }

        // Andrew

        // Declan

        // Emmanuel

        // Jadrien

    }
}


//
//
@Composable
fun ScrollingList(imageList: List<ImageData>) {
    LazyColumn {
        items(imageList) { image ->
            ImageCard(image)
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


/*
//
//
@Composable
fun rememberLazyGridState(
initialFirstVisibleItemIndex: Int = 0,
initialFirstVisibleItemScrollOffset: Int = 0
): LazyGridState {}
*/

/*
//
//
@Composable
fun DropdownMenu() {
var expanded by remember { mutableStateOf(false)}
val list = listOf("List","Grid","News","About")
var selectedItem by remember { mutableStateOf( "")}
var textFiledSize by remember { mutableStateOf(Size.Zero)}
val icon = if (expanded){
    Icons.Filled.KeyboardArrowUp
}else {
    Icons.Filled.KeyboardArrowDown
}

Column(modifier = Modifier.padding(20.dp)) {


}

}
*/





@Composable
fun TitleBar() {

    Row(modifier = Modifier.padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.mipmap.ic_launcher_foreground),
            contentDescription = "logo",
            modifier = Modifier.size(70.dp, 70.dp)
        )
        //Column() {
        Text("JWSTimager",
            color = Color.White,
            //textAlign = TextAlign.Justify,
            fontSize = 32.sp)
        // style = MaterialTheme.typography.headlineMedium
        //}

    }
}





/*
@Composable
fun DefaultPreview() {
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                TitleBar()
                //ScrollingList(imageList = SampleData.sampleImageList)
                ScrollingGridList(imageList = SampleData.sampleImageList)

            }
        }


    }
}
*/