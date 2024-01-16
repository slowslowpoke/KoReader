package com.example.koreader.ui.bookshelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookshelfViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bookshelf Fragment"
    }
    val text: LiveData<String> = _text
}