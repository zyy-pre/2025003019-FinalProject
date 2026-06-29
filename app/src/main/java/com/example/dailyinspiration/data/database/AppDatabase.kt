package com.example.dailyinspiration.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailyinspiration.data.dao.JournalDao
import com.example.dailyinspiration.data.dao.QuoteDao
import com.example.dailyinspiration.data.entity.JournalEntity
import com.example.dailyinspiration.data.entity.QuoteEntity

@Database(
    entities = [QuoteEntity::class, JournalEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "daily_inspiration_db"
                ).fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
