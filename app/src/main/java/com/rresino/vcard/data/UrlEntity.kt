package com.rresino.vcard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "urls")
data class UrlEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val url: String,
    val createdAt: Long = System.currentTimeMillis()
)