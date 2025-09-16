package com.rresino.vcard.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UrlDao {
    @Query("SELECT * FROM urls ORDER BY createdAt DESC")
    fun getAllUrls(): Flow<List<UrlEntity>>
    
    @Insert
    suspend fun insertUrl(url: UrlEntity)
    
    @Delete
    suspend fun deleteUrl(url: UrlEntity)
    
    @Query("DELETE FROM urls WHERE id = :id")
    suspend fun deleteUrlById(id: Long)
    
    @Query("SELECT COUNT(*) FROM urls")
    suspend fun getUrlCount(): Int
}