@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jwstimager.ui.theme.JWSTimagerTheme

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JWSTimagerTheme {
                MainScreen()
            }
        }

    }
}

data class ImageData(val title: String, val src_link: String, val rsc_id: Int)

@ExperimentalMaterial3Api
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TitleBar() },
        bottomBar = { BottomNavigationBar(navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        },
        //backgroundColor = colorResource(R.color.colorPrimaryDark) // Set background color to avoid the white flashing when you switch between screens
    )

}





//
//
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


//
//
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

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavItem.Home,
        NavItem.Grid,
        NavItem.Favorites,
        NavItem.News,
        NavItem.About
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.secondary,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navController.graph.startDestinationRoute?.let { route ->
                        popUp(route) {
                            saveState = true
                        }
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavItem.Home.route) {
        composable(NavItem.Home.route) {
            Home()
        }
        composable(NavItem.Grid.route) {
            Grid()
        }
        composable(NavItem.Favorites.route) {
            Favorites()
        }
        composable(NavItem.News.route) {
            News()
        }
        composable(NavItem.About.route) {
            About()
        }
    }
}