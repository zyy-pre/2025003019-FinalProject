package com.example.dailyinspiration.data.repository

import com.example.dailyinspiration.data.database.AppDatabase
import com.example.dailyinspiration.data.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

class JournalRepository(private val database: AppDatabase) {
    private val journalDao = database.journalDao()

    fun getJournalsByQuoteId(quoteId: Long): Flow<List<JournalEntity>> =
        journalDao.getJournalsByQuoteId(quoteId)

    fun getAllJournals(): Flow<List<JournalEntity>> = journalDao.getAllJournals()

    suspend fun insertJournal(quoteId: Long, userThought: String): Long =
        journalDao.insertJournal(
            JournalEntity(
                quoteId = quoteId,
                userThought = userThought,
                createdAt = System.currentTimeMillis()
            )
        )

    suspend fun deleteJournal(id: Long) = journalDao.deleteJournal(id)
}
