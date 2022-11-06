package com.example.jwstimager

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jwstimager.ui.theme.JWSTimagerTheme

//https://www.youtube.com/watch?v=4gUeyNkGE3g&t=297s&ab_channel=PhilippLackner

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.HomeScreen.route){
            HomeScreen()
            

        }

    }


}

@Composable
fun HomeScreen(){

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


//
//
@Composable
fun GridScreen(){

    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                TitleBar()
                ScrollingGridList(imageList = SampleData.sampleImageList)

            }
        }

    }


}

//
//
@Composable
fun FavoritesScreen(){

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

//
//
//
//
@Composable
fun NewsScreen(){

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
                ScrollingList(imageList = SampleData.sampleImageList)

            }
            //BottomBar()
        }


    }


}

@Composable
fun BottomBar(navController: NavController){
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        //*##############Home NAV Button##############*//
        Button(onClick = { navController.navigate("HomeScreen") }) {

            Image(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "logo",
                modifier = Modifier.size(70.dp, 70.dp)

            )

        }
        //*##############Grid NAV Button##############*//
        Button(onClick = { navController.navigate("GridScreen") }) {

            Image(
                painter = painterResource(id = R.drawable.ic_grid),
                contentDescription = "logo",
                modifier = Modifier.size(70.dp, 70.dp)

            )

        }
        //*##############Grid NAV Button##############*//
        Button(onClick = { navController.navigate("FavoritesScreen") }) {

            Image(
                painter = painterResource(id = R.drawable.ic_favorites),
                contentDescription = "logo",
                modifier = Modifier.size(70.dp, 70.dp)

            )

        }
        //*##############Grid NAV Button##############*//
        Button(onClick = { navController.navigate("NewsScreen") }) {

            Image(
                painter = painterResource(id = R.drawable.ic_news),
                contentDescription = "logo",
                modifier = Modifier.size(70.dp, 70.dp)

            )

        }
        //*##############Grid NAV Button##############*//
        Button(onClick = { navController.navigate("AboutScreen") }) {

            Image(
                painter = painterResource(id = R.drawable.ic_about),
                contentDescription = "logo",
                modifier = Modifier.size(70.dp, 70.dp)

            )

        }

    }

}
