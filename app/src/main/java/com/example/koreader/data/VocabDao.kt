package com.example.koreader.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.koreader.model.Word

@Dao
interface VocabDao {
    @Query("SELECT * FROM vocab_table ORDER BY id ASC")
    fun getAllWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(newWord: Word)
}