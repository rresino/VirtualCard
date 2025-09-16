package com.rresino.vcard.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rresino.vcard.data.UrlDatabase
import com.rresino.vcard.data.UrlEntity
import kotlinx.coroutines.launch

class QRCodeViewModel(private val database: UrlDatabase) : ViewModel() {
    
    val urls: LiveData<List<UrlEntity>> = database.urlDao().getAllUrls().asLiveData()
    
    fun addUrl(url: String) {
        viewModelScope.launch {
            val urlEntity = UrlEntity(url = url)
            database.urlDao().insertUrl(urlEntity)
        }
    }
    
    fun deleteUrl(urlEntity: UrlEntity) {
        viewModelScope.launch {
            database.urlDao().deleteUrl(urlEntity)
        }
    }
}

class QRCodeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QRCodeViewModel::class.java)) {
            val database = UrlDatabase.getDatabase(context)
            @Suppress("UNCHECKED_CAST")
            return QRCodeViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}