package com.example.jwstimager

sealed class NavItem(var route: String, var icon: Int, var title: String) {
    object Home : NavItem("home", R.drawable.ic_home, "Home")
    object Grid : NavItem("grid", R.drawable.ic_grid, "Grid")
    object Favorites : NavItem("favorites", R.drawable.ic_favorites, "Favorites")
    object News : NavItem("news", R.drawable.ic_news, "News")
    object About : NavItem("about", R.drawable.ic_about, "About")
}