package com.example.koreader.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.koreader.model.Word

@Database(entities = [Word::class], version = 1)
abstract class VocabDatabase: RoomDatabase() {
    abstract fun vocabDao(): VocabDao

    companion object {

        @Volatile
        private var INSTANCE: VocabDatabase? = null

        fun getDatabase(context: Context): VocabDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VocabDatabase::class.java,
                    name = "vocab_database"
                ).build()
                INSTANCE = instance
                return instance
            }


        }
    }

}