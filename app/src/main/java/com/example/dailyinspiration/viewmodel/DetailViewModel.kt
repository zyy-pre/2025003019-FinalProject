package com.example.dailyinspiration.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyinspiration.data.entity.JournalEntity
import com.example.dailyinspiration.data.entity.QuoteEntity
import com.example.dailyinspiration.data.repository.JournalRepository
import com.example.dailyinspiration.data.repository.QuoteRepository
import com.example.dailyinspiration.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailUiState(
    val isLoading: Boolean = true,
    val quote: QuoteEntity? = null,
    val journals: List<JournalEntity> = emptyList(),
    val error: String? = null
)

class DetailViewModel(
    application: Application,
    private val quoteRepository: QuoteRepository,
    private val journalRepository: JournalRepository,
    private val prefs: UserPreferencesRepository
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun loadQuote(quoteId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val quote = quoteRepository.getQuoteById(quoteId)
                if (quote != null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        quote = quote
                    )
                    loadJournals(quoteId)
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "名言未找到"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun loadJournals(quoteId: Long) {
        viewModelScope.launch {
            journalRepository.getJournalsByQuoteId(quoteId).collect { journals ->
                _uiState.value = _uiState.value.copy(journals = journals)
            }
        }
    }

    fun addJournal(quoteId: Long, thought: String) {
        if (thought.isBlank()) return
        viewModelScope.launch {
            try {
                journalRepository.insertJournal(quoteId, thought)
                _uiState.value = _uiState.value.copy(
                    quote = _uiState.value.quote
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun deleteJournal(journalId: Long) {
        viewModelScope.launch {
            journalRepository.deleteJournal(journalId)
        }
    }

    fun deleteQuote(id: Long) {
        viewModelScope.launch {
            quoteRepository.deleteQuote(id)
        }
    }
}
