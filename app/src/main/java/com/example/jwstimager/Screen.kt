package com.example.jwstimager

sealed class Screen(val route: String){
    object HomeScreen : Screen("home_screen")
    object GridScreen : Screen("grid_screen")
    object FavoritesScreen : Screen("favorites_screen")
    object NewsScreen : Screen("news_screen")
    object AboutScreen : Screen("about_screen")

}
