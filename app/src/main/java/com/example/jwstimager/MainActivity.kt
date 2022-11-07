package com.example.jwstimager

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jwstimager.Database.LinkListAdapter
import androidx.compose.ui.window.PopupProperties
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jwstimager.ui.theme.JWSTimagerTheme
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

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
            Modifier.fillMaxSize().padding(12.dp),
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
            Modifier.fillMaxSize().padding(12.dp).offset(y = 60.dp),
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
                    favIcon = Icons.Filled.CheckCircle
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

@Composable
fun GalleryImageCard(image: ImageData) {

    var isFavorite by remember { mutableStateOf(false) }
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
                .clickable { isFavorite = !isFavorite }
        )


        Box(
            Modifier.fillMaxSize().padding(12.dp).offset(y = 60.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            val context = LocalContext.current
            IconButton(
                onClick = { isFavorite = !isFavorite }
            ) {
                if (isFavorite) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(25.dp)
                    )
                    image.isFavorite = true
                }
                else {
                    image.isFavorite = false
                }

            }
        }
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
internal fun ScrollingGridList(imageList: List<ImageData>){

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

    Row(modifier = Modifier.padding(all = 8.dp), verticalAlignment = Alignment.CenterVertically) {
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