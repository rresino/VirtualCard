package com.rresino.vcard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UrlEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UrlDatabase : RoomDatabase() {
    abstract fun urlDao(): UrlDao
    
    companion object {
        @Volatile
        private var INSTANCE: UrlDatabase? = null
        
        fun getDatabase(context: Context): UrlDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UrlDatabase::class.java,
                    "url_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}