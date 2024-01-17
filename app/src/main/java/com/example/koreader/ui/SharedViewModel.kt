package com.example.koreader.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val wordList = MutableLiveData(listOf("hobbit", "comfort", "hole"))

    fun addWord(newWord: String){
        wordList.value = wordList.value?.plus(newWord)
    }



}