package com.example.koreader.ui.vocab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VocabViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is vocab Fragment"
    }
    val text: LiveData<String> = _text
}