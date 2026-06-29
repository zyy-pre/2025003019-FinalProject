package com.example.dailyinspiration.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object CollectionList : Screen("collection_list")
    data object Detail : Screen("detail/{collectionId}") {
        fun createRoute(collectionId: Long) = "detail/$collectionId"
    }
    data object Settings : Screen("settings")
}
