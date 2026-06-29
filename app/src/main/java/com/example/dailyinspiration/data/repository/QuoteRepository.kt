package com.example.dailyinspiration.data.repository

import com.example.dailyinspiration.data.database.AppDatabase
import com.example.dailyinspiration.data.network.NetworkModule
import com.example.dailyinspiration.model.InspirationItem

class QuoteRepository(private val database: AppDatabase) {
    private val apiService = NetworkModule.quoteApiService
    private val quoteDao = database.quoteDao()

    private val backgroundImages = listOf(
        "https://picsum.photos/id/1015/800/600",
        "https://picsum.photos/id/1016/800/600",
        "https://picsum.photos/id/1018/800/600",
        "https://picsum.photos/id/1019/800/600",
        "https://picsum.photos/id/1022/800/600",
        "https://picsum.photos/id/1035/800/600",
        "https://picsum.photos/id/1036/800/600",
        "https://picsum.photos/id/1039/800/600",
        "https://picsum.photos/id/1040/800/600",
        "https://picsum.photos/id/1043/800/600",
        "https://picsum.photos/id/1047/800/600",
        "https://picsum.photos/id/1050/800/600",
        "https://picsum.photos/id/1055/800/600",
        "https://picsum.photos/id/1057/800/600",
        "https://picsum.photos/id/1058/800/600",
        "https://picsum.photos/id/1067/800/600",
        "https://picsum.photos/id/1069/800/600",
        "https://picsum.photos/id/1073/800/600",
        "https://picsum.photos/id/1076/800/600",
        "https://picsum.photos/id/1080/800/600"
    )

    suspend fun getRandomInspiration(): Result<InspirationItem> {
        return try {
            val quoteDto = apiService.getRandomQuote()
            val imageUrl = backgroundImages.random()
            Result.success(
                InspirationItem(
                    quoteId = quoteDto.id.toString(),
                    quoteText = quoteDto.quote,
                    author = quoteDto.author,
                    imageUrl = imageUrl
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun collectQuote(text: String, author: String, emotionTag: String) {
        val existing = quoteDao.findQuoteByContent(text, author)
        if (existing != null) {
            quoteDao.collectQuote(existing.id, emotionTag)
        } else {
            quoteDao.insertQuote(
                com.example.dailyinspiration.data.entity.QuoteEntity(
                    text = text,
                    author = author,
                    emotionTag = emotionTag,
                    isCollected = true,
                    collectedTime = System.currentTimeMillis()
                )
            )
        }
    }

    fun getCollectedQuotes() = quoteDao.getCollectedQuotes()

    fun getCollectedQuotesByTag(tag: String) = quoteDao.getCollectedQuotesByTag(tag)

    suspend fun getQuoteById(id: Long) = quoteDao.getQuoteById(id)

    fun getCollectedCount() = quoteDao.getCollectedCount()

    suspend fun deleteQuote(id: Long) = quoteDao.deleteQuote(id)

    suspend fun uncollectQuote(id: Long) = quoteDao.uncollectQuote(id)

    suspend fun getRandomCollectedQuote() = quoteDao.getRandomCollectedQuote()

    suspend fun getRandomCollectedQuoteExcluding(id: Long) = quoteDao.getRandomCollectedQuoteExcluding(id)
}
