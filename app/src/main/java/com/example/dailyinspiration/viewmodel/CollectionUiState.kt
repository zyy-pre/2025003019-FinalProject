package com.example.dailyinspiration.viewmodel

import com.example.dailyinspiration.data.entity.QuoteEntity

data class CollectionUiState(
    val isLoading: Boolean = true,
    val collectedQuotes: List<QuoteEntity> = emptyList(),
    val selectedTag: String? = null,
    val error: String? = null,
    val totalCount: Int = 0
)
