package com.example.koreader.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.koreader.model.Word

class SharedViewModel: ViewModel() {
    val wordList = MutableLiveData(listOf(
        Word(0,"brother","hermano"),
        Word(1, "cat", "gato"),
        Word(2,"queen", "reya")
    ))

    fun addWord(newWord: Word){
        //тут не уверена что таким способом обновляется список. Он вообще эффективный? каждый раз новый...
        wordList.value = wordList.value?.plus(newWord)
    }



}