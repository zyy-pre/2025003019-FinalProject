package com.example.dailyinspiration.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyinspiration.data.repository.QuoteRepository
import com.example.dailyinspiration.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application,
    private val quoteRepository: QuoteRepository,
    private val prefs: UserPreferencesRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _isNetworkAvailable = MutableStateFlow(true)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _isDarkMode.value = prefs.isDarkMode.first()
            } catch (_: Exception) {
                _isDarkMode.value = false
            }
        }
        loadInspiration()
    }

    fun loadInspiration() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = quoteRepository.getRandomInspiration()
            result.fold(
                onSuccess = { item ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        inspiration = item,
                        isCollected = false
                    )
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "加载失败，请检查网络连接"
                    )
                }
            )
        }
    }

    fun showEmotionDialog() {
        _uiState.value = _uiState.value.copy(showEmotionDialog = true)
    }

    fun dismissEmotionDialog() {
        _uiState.value = _uiState.value.copy(showEmotionDialog = false)
    }

    fun collectWithEmotion(emotionTag: String) {
        val item = _uiState.value.inspiration ?: return
        viewModelScope.launch {
            try {
                quoteRepository.collectQuote(
                    text = item.quoteText,
                    author = item.author,
                    emotionTag = emotionTag
                )
                _uiState.value = _uiState.value.copy(
                    isCollected = true,
                    showEmotionDialog = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    showEmotionDialog = false
                )
            }
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            val newValue = !_isDarkMode.value
            _isDarkMode.value = newValue
            prefs.setDarkMode(newValue)
        }
    }
}
