package com.example.jwstimager

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.drawable.toBitmap
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageRequest
import com.example.jwstimager.ui.theme.JWSTimagerTheme
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File

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
                    ScrollingList(imageList = SampleData.sampleImageList)
                }
            }
            ImageCard(
                ImageData(
                    "google",
                    "some link",
                    R.drawable.jwst_in_space_cc
                )
            )

        }

    }

}


data class ImageData(val title: String, val src_link: String, val rsc_id: Int)
@Composable
fun ImageCard(image: ImageData) {
    val coroutineScope = rememberCoroutineScope()
    // keep track of whether or not the image card is expanded
    var isExpanded by remember { mutableStateOf(false) }
    // surfaceColor will be updated gradually from one color to the other
    val surfaceColor by animateColorAsState(
        if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    )
    Surface(
        //shape = MaterialTheme.shapes.medium, // couldn't add shape to surface
        shadowElevation = 1.dp,
        // surfaceColor color will be changing gradually from primary to surface
        color = surfaceColor,
        // animateContentSize will change the Surface size gradually
        modifier = Modifier
            .animateContentSize()
            .padding(1.dp)
    ) {
        Column(modifier = Modifier.padding(all = 8.dp)) {

            Image(
                painter = painterResource(id = image.rsc_id),
                contentDescription = "This is a test image of the JWST",
                modifier = Modifier
                    //.size(300.dp)
                    //.border(1.5.dp, MaterialTheme.colorScheme.primary)
                    // toggle is expanded by clicking on the image
                    .clickable { isExpanded = !isExpanded }
                    .fillMaxWidth()


            )

            val context = LocalContext.current
            val uri = ContextCompat.getCodeCacheDir(context)

            // Intent to show the share screen
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, "From JWST Imager App")
                type = "image/*"
            }
            val shareIntent = Intent.createChooser(intent, "Share")

            /*
            // Need coil import

            val imageLoader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(image.src_link)
                .build()
                */


            Button( onClick = {

                val description = "This is from the JWST Imager App!!!!"
                context.startActivity(shareIntent)

                /*
                coroutineScope.launch {
                    val drawable = imageLoader.execute(request).drawable
                    val description = "This is from the JWST Imager App!!!!"
                    val share = "Share"

                    //Get Bitmap from your imageView
                    val bitmap = drawable?.toBitmap() // your imageView here.

                    //Compress image
                    val bytes = ByteArrayOutputStream()
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

                    //Save image & get path of it
                    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "tempimage", null)

                    //Get URI of saved image
                    val uri = Uri.parse(path)

                    //Put Uri as extra to share intent
                    intent.putExtra(Intent.EXTRA_STREAM, uri)

                    //Start/Launch Share intent
                    startActivity(
                        context,
                        Intent.createChooser(intent, share), null
                    )
                }
                */


            }) {
                Text("Share")
            }


            AnimatedVisibility(visible = isExpanded) {
                //Spacer(modifier = Modifier.width(8.dp))

                Row (
                    Modifier
                        .wrapContentWidth()
                        .padding(all = 4.dp)
                ){
                    Text(
                        text = image.title,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = image.src_link,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}



@Composable
fun ScrollingList(imageList: List<ImageData>) {
    LazyColumn {
        items(imageList) { image ->
            ImageCard(image)
        }
    }
}

@Composable
fun TitleBar() {
    Column(modifier = Modifier
        .padding(24.dp)
        .fillMaxWidth()
    ) {
        Text(text = "JWSTimager")
    }
}





//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    JWSTimagerTheme {
//        Surface {
//            MessageCard(msg = Message("John", "Sup Sup. This is a test composable!"))
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TitleBar()
                ScrollingList(imageList = SampleData.sampleImageList)
            }
        }

    }

}
