package com.example.jwstimager

sealed class NavItem(var route: String, var icon: Int, var title: String) {
    object Home : NavItem("home", R.drawable.Home, "Home")
    object Grid : NavItem("grid", R.drawable.Grid, "Grid")
    object Favorites : NavItem("favorites", R.drawable.Favorites, "Favorites")
    object News : NavItem("news", R.drawable.News, "News")
    object About : NavItem("about", R.drawable.About, "About")
}