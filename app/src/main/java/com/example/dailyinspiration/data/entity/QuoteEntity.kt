package com.example.dailyinspiration.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val author: String,
    val emotionTag: String,
    val isCollected: Boolean = false,
    val collectedTime: Long = System.currentTimeMillis()
)
