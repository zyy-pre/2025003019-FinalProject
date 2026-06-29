package com.example.dailyinspiration.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.dailyinspiration.ui.screens.CollectionListScreen
import com.example.dailyinspiration.ui.screens.DetailScreen
import com.example.dailyinspiration.ui.screens.HomeScreen
import com.example.dailyinspiration.ui.screens.SettingsScreen
import com.example.dailyinspiration.viewmodel.CollectionViewModel
import com.example.dailyinspiration.viewmodel.DetailViewModel
import com.example.dailyinspiration.viewmodel.HomeViewModel

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun AppNavigation(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    collectionViewModel: CollectionViewModel,
    detailViewModel: DetailViewModel
) {
    val bottomNavItems = listOf(
        BottomNavItem("今日灵感", Icons.Default.Home, Screen.Home.route),
        BottomNavItem("我的收藏", Icons.Default.Favorite, Screen.CollectionList.route),
        BottomNavItem("设置", Icons.Default.Settings, Screen.Settings.route)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.CollectionList.route,
        Screen.Settings.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            icon = {
                                AnimatedVisibility(
                                    visible = selected,
                                    enter = scaleIn(
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        )
                                    ) + fadeIn(),
                                    exit = scaleOut() + fadeOut()
                                ) {
                                    Icon(item.icon, contentDescription = item.label)
                                }
                                AnimatedVisibility(
                                    visible = !selected,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    Icon(item.icon, contentDescription = item.label)
                                }
                            },
                            label = {
                                AnimatedVisibility(
                                    visible = selected,
                                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                                ) {
                                    Text(item.label, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }
                                AnimatedVisibility(
                                    visible = !selected,
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    Text(item.label, fontSize = 11.sp)
                                }
                            },
                            selected = selected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(Screen.Home.route) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = homeViewModel
                )
            }
            composable(Screen.CollectionList.route) {
                LaunchedEffect(Unit) {
                    collectionViewModel.refreshCollections()
                }
                CollectionListScreen(
                    viewModel = collectionViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("collectionId") { type = NavType.LongType })
            ) { backStackEntry ->
                val collectionId = backStackEntry.arguments?.getLong("collectionId") ?: return@composable
                DetailScreen(
                    viewModel = detailViewModel,
                    collectionId = collectionId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    viewModel = homeViewModel
                )
            }
        }
    }
}
