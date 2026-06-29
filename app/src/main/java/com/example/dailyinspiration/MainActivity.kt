package com.example.dailyinspiration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.dailyinspiration.data.database.AppDatabase
import com.example.dailyinspiration.data.repository.JournalRepository
import com.example.dailyinspiration.data.repository.QuoteRepository
import com.example.dailyinspiration.datastore.UserPreferencesRepository
import com.example.dailyinspiration.navigation.AppNavigation
import com.example.dailyinspiration.ui.theme.DailyInspirationTheme
import com.example.dailyinspiration.viewmodel.CollectionViewModel
import com.example.dailyinspiration.viewmodel.DetailViewModel
import com.example.dailyinspiration.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val quoteRepository = QuoteRepository(database)
        val journalRepository = JournalRepository(database)
        val prefs = UserPreferencesRepository(applicationContext)

        val homeViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(application, quoteRepository, prefs) as T
                }
            }
        )[HomeViewModel::class.java]

        val collectionViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CollectionViewModel(quoteRepository) as T
                }
            }
        )[CollectionViewModel::class.java]

        val detailViewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DetailViewModel(application, quoteRepository, journalRepository, prefs) as T
                }
            }
        )[DetailViewModel::class.java]

        setContent {
            val isDarkMode by homeViewModel.isDarkMode.collectAsState()

            DailyInspirationTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(
                        navController = navController,
                        homeViewModel = homeViewModel,
                        collectionViewModel = collectionViewModel,
                        detailViewModel = detailViewModel
                    )
                }
            }
        }
    }
}
