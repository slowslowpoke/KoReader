package com.example.koreader.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vocab_table")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @NonNull
    val original: String,
    @NonNull
    val translated: String
) {
}