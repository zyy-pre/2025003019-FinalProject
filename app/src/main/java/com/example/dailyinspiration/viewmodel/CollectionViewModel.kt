package com.example.dailyinspiration.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyinspiration.data.entity.QuoteEntity
import com.example.dailyinspiration.data.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectionUiState())
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()

    private var allCollected: List<QuoteEntity> = emptyList()

    init {
        loadCollections()
    }

    private fun loadCollections() {
        viewModelScope.launch {
            quoteRepository.getCollectedCount().collect { count ->
                _uiState.value = _uiState.value.copy(totalCount = count)
            }
        }
        viewModelScope.launch {
            quoteRepository.getCollectedQuotes().collect { quotes ->
                allCollected = quotes
                applyFilter()
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun onTagSelected(tag: String?) {
        _uiState.value = _uiState.value.copy(selectedTag = tag)
        applyFilter()
    }

    private fun applyFilter() {
        val tag = _uiState.value.selectedTag
        val filtered = if (tag != null) {
            allCollected.filter { it.emotionTag == tag }
        } else {
            allCollected
        }
        _uiState.value = _uiState.value.copy(collectedQuotes = filtered)
    }

    fun deleteQuote(id: Long) {
        viewModelScope.launch {
            quoteRepository.deleteQuote(id)
        }
    }

    fun refreshCollections() {
        loadCollections()
    }
}
