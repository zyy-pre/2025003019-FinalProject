package com.example.dailyinspiration.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dailyinspiration.data.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote WHERE isCollected = 1 ORDER BY collectedTime DESC")
    fun getCollectedQuotes(): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quote WHERE isCollected = 1 AND emotionTag = :tag ORDER BY collectedTime DESC")
    fun getCollectedQuotesByTag(tag: String): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quote WHERE id = :id")
    suspend fun getQuoteById(id: Long): QuoteEntity?

    @Query("SELECT * FROM quote WHERE isCollected = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCollectedQuote(): QuoteEntity?

    @Query("SELECT COUNT(*) FROM quote WHERE isCollected = 1")
    fun getCollectedCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity): Long

    @Query("UPDATE quote SET isCollected = 1, emotionTag = :emotionTag, collectedTime = :collectedTime WHERE id = :id")
    suspend fun collectQuote(id: Long, emotionTag: String, collectedTime: Long = System.currentTimeMillis())

    @Query("DELETE FROM quote WHERE id = :id")
    suspend fun deleteQuote(id: Long)

    @Query("UPDATE quote SET isCollected = 0 WHERE id = :id")
    suspend fun uncollectQuote(id: Long)

    @Query("SELECT * FROM quote WHERE text = :text AND author = :author LIMIT 1")
    suspend fun findQuoteByContent(text: String, author: String): QuoteEntity?

    @Query("SELECT * FROM quote WHERE isCollected = 1 AND id != :excludeId ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCollectedQuoteExcluding(excludeId: Long): QuoteEntity?
}
