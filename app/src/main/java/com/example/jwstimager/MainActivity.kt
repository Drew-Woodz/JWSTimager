package com.example.jwstimager

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jwstimager.ui.theme.JWSTimagerTheme

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
                    DefaultPreview()
                }
            }
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
        if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
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
                    //toggle is expanded by clicking on the image
                    .clickable { isExpanded = !isExpanded }
                    .fillMaxSize()
            )

            val context = LocalContext.current

            Button(modifier = Modifier.align(Alignment.End),
                onClick={

                    context.sharing(image.rsc_id)
                    /*
                    val state = painter.state as? AsyncImagePainter.State.Success
                    val drawable = state?.result?.drawable
                    if (drawable != null) {
                        context.shareImage(
                            "Share image via",
                            drawable,
                            "testimage1"
                        )
                    }
                    */
                }
            ) {
                Text(text = "Share", color = Color.White)
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

fun Context.sharing(imageName: Int){
    val b = BitmapFactory.decodeResource(resources, imageName)
    val path = MediaStore.Images.Media.insertImage(contentResolver, b, "Image", null )

    val uriPath = Uri.parse(path)

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "From JWST Imager App")
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uriPath)
    }
    startActivity(shareIntent)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                TitleBar()
                ScrollingList(imageList = SampleData.sampleImageList)
            }
        }


    }
}
