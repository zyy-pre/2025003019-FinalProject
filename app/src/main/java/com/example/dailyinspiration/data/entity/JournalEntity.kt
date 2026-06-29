package com.example.dailyinspiration.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "journal",
    foreignKeys = [
        ForeignKey(
            entity = QuoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["quoteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("quoteId")]
)
data class JournalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quoteId: Long,
    val userThought: String,
    val createdAt: Long = System.currentTimeMillis()
)
