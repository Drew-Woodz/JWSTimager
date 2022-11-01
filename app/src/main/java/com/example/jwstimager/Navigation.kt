package com.example.jwstimager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jwstimager.ui.theme.JWSTimagerTheme

//https://www.youtube.com/watch?v=4gUeyNkGE3g&t=297s&ab_channel=PhilippLackner

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController)
            //GalleryScreen(navController = navController)

        }

    }


}

@Composable
fun MainScreen(navController: NavController){

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
fun GalleryScreen(navController: NavController){

    Column(){
        //TODO
        TitleBar()
        ScrollingGridList(imageList = SampleData.sampleImageList)
    }


}

//
//
@Composable
fun NewsScreen(navController: NavController){
    var text by remember {
        mutableStateOf( "")
    }
    Column(){
        //TODO
        TitleBar()
    }


}