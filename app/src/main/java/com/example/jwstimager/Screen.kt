package com.example.jwstimager

sealed class Screen(val route: String){
    object MainScreen : Screen("main_screen")
    object GalleryScreen : Screen("gallery_screen")
    object NewsScreen : Screen("news_screen")

}
