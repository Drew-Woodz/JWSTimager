package com.example.jwstimager


import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
){
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.ic_home
    )
    object Grid: BottomBarScreen(
        route = "grid",
        title = "Grid",
        icon = R.drawable.ic_grid
    )
    object Favorites : BottomBarScreen(
        route = "favorites",
        title = "Favorites",
        icon =  R.drawable.ic_favorites
    )
    object News : BottomBarScreen(
        route = "news",
        title = "News",
        icon =  R.drawable.ic_news
    )
    object About : BottomBarScreen(
        route = "about",
        title = "About",
        icon = R.drawable.ic_about
    )
}
