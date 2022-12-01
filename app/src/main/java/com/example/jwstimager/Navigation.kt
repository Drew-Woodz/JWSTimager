package com.example.jwstimager

import android.graphics.drawable.shapes.OvalShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jwstimager.ui.theme.JWSTimagerTheme
import java.lang.Math.round
//import java.util.ArrayList
import kotlin.collections.ArrayList

//https://www.youtube.com/watch?v=4gUeyNkGE3g&t=297s&ab_channel=PhilippLackner


@Composable
fun Navigation(posts : ArrayList<Post>, scraper: flickrScrape){
    val navController = rememberNavController()
    Column(
        Modifier
            .fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(scraper)
            }
            composable(Screen.GridScreen.route) {
                GridScreen(scraper)
            }
            composable(Screen.FavoritesScreen.route) {
                FavoritesScreen(scraper)
            }
            composable(Screen.NewsScreen.route) {
                NewsScreen(posts)
            }
            composable(Screen.AboutScreen.route) {
                AboutScreen()
            }
        }
        NavBar(navController = navController)
    }



}

@Composable
fun NavBar(navController: NavController) {
Column() {
        Row(

            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeightIn(70.dp)
                // .height(height = 55.dp)
                .offset(y = (-20).dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(3.dp)
            //.border(1.dp, color = Color(0xFFFFFFFF))

        ) {
            //*##############Home NAV Button##############*//
            Button(onClick = { navController.navigate(Screen.HomeScreen.route) },
                modifier = Modifier
                    .border(1.dp, color = Color(0x8899abC8), shape = RoundedCornerShape(20.dp))
                    .padding(3.dp)


            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "logo",
                    //modifier = Modifier.size(30.dp, 30.dp)

                )

            }
            //*##############Grid NAV Button##############*//
            Button(onClick = { navController.navigate(Screen.GridScreen.route) },
                modifier = Modifier
                    .border(1.dp, color = Color(0x8899abC8), shape = RoundedCornerShape(20.dp))
                    .padding(3.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_grid),
                    contentDescription = "logo",
                    //modifier = Modifier.size(70.dp, 70.dp)

                )

            }
            //*##############Grid NAV Button##############*//
            Button(onClick = { navController.navigate(Screen.FavoritesScreen.route) },
                modifier = Modifier
                    .border(1.dp, color = Color(0x8899abC8), shape = RoundedCornerShape(20.dp))
                    .padding(3.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_favorites),
                    contentDescription = "logo",
                    //modifier = Modifier.size(70.dp, 70.dp)

                )

            }
            //*##############Grid NAV Button##############*//
            Button(onClick = { navController.navigate(Screen.NewsScreen.route) },
                modifier = Modifier
                    .border(1.dp, color = Color(0x8899abC8), shape = RoundedCornerShape(20.dp))
                    .padding(3.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_news),
                    contentDescription = "logo",
                    //modifier = Modifier.size(70.dp, 70.dp)

                )

            }
            //*##############Grid NAV Button##############*//
            Button(onClick = { navController.navigate(Screen.AboutScreen.route) },
                modifier = Modifier
                    .border(1.dp, color = Color(0x8899abC8), shape = RoundedCornerShape(20.dp))
                    .padding(3.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = "logo",
                    //modifier = Modifier.size(70.dp, 70.dp)

                )

            }

        }

    }
}

@Composable
fun HomeScreen(scraper : flickrScrape){
    //scraper.scrape()
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(Modifier.background(MaterialTheme.colorScheme.onBackground)) {
                TitleBar()
                Text(
                    text = "Home",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.CenterHorizontally)

                )
                ScrollingImageList(imageList = scraper.getURLs())
            }
        }


    }
}


//
//
@Composable
fun GridScreen(scraper: flickrScrape){
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(Modifier.background(MaterialTheme.colorScheme.onBackground)) {
                TitleBar()
                ScrollingGridList(imageList = scraper.getURLs())
            }
        }
    }
}

//
//
@Composable
fun FavoritesScreen(scraper: flickrScrape){
    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(Modifier.background(MaterialTheme.colorScheme.onBackground)) {
                TitleBar()
                Text(
                    text = "Favorites",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.CenterHorizontally)

                )
                ScrollingImageList(imageList = scraper.getURLs())
            }
        }


    }


}

//
//
//
//
@Composable
fun NewsScreen(posts: ArrayList<Post>){

    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                TitleBar()
                ScrollingNewsList(posts)


            }
        }


    }


}

//
//
//
//
@Composable
fun AboutScreen(){

    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                TitleBar()
                AboutPage()
            }
        }


    }


}

