package com.example.dailyinspiration.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dailyinspiration.data.entity.JournalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal WHERE quoteId = :quoteId ORDER BY createdAt DESC")
    fun getJournalsByQuoteId(quoteId: Long): Flow<List<JournalEntity>>

    @Query("SELECT * FROM journal ORDER BY createdAt DESC")
    fun getAllJournals(): Flow<List<JournalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: JournalEntity): Long

    @Query("DELETE FROM journal WHERE id = :id")
    suspend fun deleteJournal(id: Long)
}
