package com.example.jwstimager

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.RowScopeInstance.align
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
    Column(
        Modifier
            .fillMaxSize()
    ) {
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
            composable(route = Screen.HomeScreen.route) {
                HomeScreen()
            }
            composable(Screen.GridScreen.route) {
                GridScreen()
            }
            composable(Screen.FavoritesScreen.route) {
                FavoritesScreen()
            }
            composable(Screen.NewsScreen.route) {
                NewsScreen()
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
//    Box (Modifier
//        .requiredHeight(35.dp)
//        .fillMaxSize()
//        //.padding(bottom = 200.dp)
//        .offset(y = (-20).dp)
//    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeightIn(70.dp)
                .offset(y = (-20).dp)

            // horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            //*##############Home NAV Button##############*//
            Button(onClick = { navController.navigate(Screen.HomeScreen.route) }) {

                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "logo",
                    //modifier = Modifier.size(70.dp, 70.dp)

                )

            }
            //*##############Grid NAV Button##############*//
            Button(onClick = { navController.navigate(Screen.GridScreen.route) }) {

                Image(
                    painter = painterResource(id = R.drawable.ic_grid),
                    contentDescription = "logo",
                    //modifier = Modifier.size(70.dp, 70.dp)

                )

            }
            //*##############Grid NAV Button##############*//
            Button(onClick = { navController.navigate(Screen.FavoritesScreen.route) }) {

                Image(
                    painter = painterResource(id = R.drawable.ic_favorites),
                    contentDescription = "logo",
                    //modifier = Modifier.size(70.dp, 70.dp)

                )

            }
            //*##############Grid NAV Button##############*//
            Button(onClick = { navController.navigate(Screen.NewsScreen.route) }) {

                Image(
                    painter = painterResource(id = R.drawable.ic_news),
                    contentDescription = "logo",
                    //modifier = Modifier.size(70.dp, 70.dp)

                )

            }
            //*##############Grid NAV Button##############*//
            Button(onClick = { navController.navigate(Screen.AboutScreen.route) }) {

                Image(
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = "logo",
                    //modifier = Modifier.size(70.dp, 70.dp)

                )

            }

        }
//    }
}

@Composable
fun HomeScreen(){

    JWSTimagerTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
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
        }


    }


}

