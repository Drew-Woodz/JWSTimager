package com.example.jwstimager

<<<<<<< HEAD
<<<<<<< HEAD
import android.os.Bundle
=======
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
=======

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
>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
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
<<<<<<< HEAD
<<<<<<< HEAD
=======
import androidx.compose.material3.Button
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
=======
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
<<<<<<< HEAD
<<<<<<< HEAD
=======
import androidx.compose.ui.platform.LocalContext
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
=======
import androidx.compose.ui.platform.LocalContext
>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
<<<<<<< HEAD
<<<<<<< HEAD
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jwstimager.Database.LinkListAdapter
=======
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
=======
import androidx.compose.ui.window.PopupProperties
>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
import com.example.jwstimager.ui.theme.JWSTimagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
<<<<<<< HEAD
            /*val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
            val adapter = LinkListAdapter()
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)*/
=======
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
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
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
=======

>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
    }
}

data class ImageData(val title: String, val src_link: String, val rsc_id: Int)

<<<<<<< HEAD
<<<<<<< HEAD
@Composable
fun ImageCard(image: ImageData) {

=======

@Composable
fun ImageCard(image: ImageData) {

    val coroutineScope = rememberCoroutineScope()
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
=======

//
//
@Composable
fun ImageCard(image: ImageData) {

    val coroutineScope = rememberCoroutineScope()
>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
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
<<<<<<< HEAD
<<<<<<< HEAD
        Column(modifier = Modifier.padding(all = 8.dp)) {
=======

        Column(modifier = Modifier.padding(all = 8.dp)) {

>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
=======

        Column(modifier = Modifier.padding(all = 8.dp)) {

>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
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
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6

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


<<<<<<< HEAD
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
=======
>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
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

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======

//
//
>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
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


<<<<<<< HEAD
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
=======
//
//
>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
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
                    ImageCard(image)
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

<<<<<<< HEAD
<<<<<<< HEAD
    Row(modifier = Modifier.padding(all = 8.dp)) {
=======
    Row(modifier = Modifier.padding(all = 8.dp), verticalAlignment = Alignment.CenterVertically) {
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = "logo",
                modifier = Modifier.size(70.dp, 70.dp)

            )
<<<<<<< HEAD
            Column() {
                Text("JWSTimager",
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    fontSize = 32.sp)
                // style = MaterialTheme.typography.headlineMedium
            }
=======
            //Column() {
                Text("JWSTimager",
                    color = Color.White,
                    //textAlign = TextAlign.Justify,
                    fontSize = 32.sp)
                // style = MaterialTheme.typography.headlineMedium
            //}
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
=======
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
>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6

    }
}

<<<<<<< HEAD
<<<<<<< HEAD
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    JWSTimagerTheme {
//        Surface {
//            MessageCard(msg = Message("John", "Sup Sup. This is a test composable!"))
//        }
//    }
//}

=======
>>>>>>> d4d118328d0829d0b0b9dc3eba8f269dee071f3b
@Preview(showBackground = true)
=======


>>>>>>> 207dd1e8f984e1a953b0058736a922a31cd037c6
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
