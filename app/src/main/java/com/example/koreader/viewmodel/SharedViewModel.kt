package com.example.koreader.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koreader.data.VocabDao
import com.example.koreader.data.VocabDatabase
import com.example.koreader.model.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(application: Application): AndroidViewModel(application) {
    val wordList: LiveData<List<Word>>
    private val userDao: VocabDao
    init{
        userDao = VocabDatabase.getDatabase(application).vocabDao()
        wordList = userDao.getAllWords()
    }
    fun addWord(newWord: Word){
        viewModelScope.launch ( Dispatchers.IO){
            userDao.addWord(newWord)
        }
    }



}