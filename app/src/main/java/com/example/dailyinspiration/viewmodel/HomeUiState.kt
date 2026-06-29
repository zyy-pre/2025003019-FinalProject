package com.example.dailyinspiration.viewmodel

import com.example.dailyinspiration.model.InspirationItem

data class HomeUiState(
    val isLoading: Boolean = true,
    val inspiration: InspirationItem? = null,
    val error: String? = null,
    val isCollected: Boolean = false,
    val showEmotionDialog: Boolean = false
)
