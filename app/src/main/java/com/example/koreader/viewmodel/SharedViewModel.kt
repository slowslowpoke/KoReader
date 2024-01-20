package com.example.koreader.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.koreader.data.VocabDao
import com.example.koreader.data.VocabDatabase
import com.example.koreader.model.Word
import com.example.koreader.model.YandexTranslateModel.TranslateRequestBody
import com.example.koreader.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

enum class ApiRequestStatus {
    ERROR_NO_INTERNET,
    ERROR_UNEXPECTED_RESPONSE,
    DONE_SUCCESSFUL,
    DONE_UNSUCCESSFUL,
    NOTHING_TO_PROCESS
}

private const val FOLDER_ID = "b1gpne4qo6st3l5euhsh"
private const val LANGUEAGE_CODE_KAZ = "kk"
private const val TAG = "SharedViewModel"

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    //Нужна чтобы кинуть запрос к яндекс апи. Может все константы из разных ретрофитных файлов потом в один файл с константами??

    var yandexApiRequestStatus = MutableLiveData<ApiRequestStatus>()

    val vocabList: LiveData<List<Word>>
    private val userDao: VocabDao

    init {
        userDao = VocabDatabase.getDatabase(application).vocabDao()
        vocabList = userDao.getAllWords()
    }

    fun addWord(newWord: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.addWord(newWord)
        }
    }

    //потом заменить target язык на ENUM
    suspend fun getWordTranslation(
        wordToTranslate: String,
        targetLangCode: String = LANGUEAGE_CODE_KAZ
    ): Pair<String, String>? {
        //Готовим тело запроса на перевод

        val translateRequestBody = TranslateRequestBody(
            folderId = FOLDER_ID,
            texts = listOf(wordToTranslate),
            targetLanguageCode = targetLangCode
        )


        val response = try {
            RetrofitInstance.api.getWordTranslation(translateRequestBody)
        } catch (e: IOException) {
            Log.e(TAG, "IOException, check your internet connection")
            yandexApiRequestStatus.postValue(ApiRequestStatus.ERROR_NO_INTERNET)
            return null
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException, unexpected response")
            yandexApiRequestStatus.postValue(ApiRequestStatus.ERROR_UNEXPECTED_RESPONSE)
            return null
        }
        if (response.isSuccessful && response.body() != null) {
            yandexApiRequestStatus.postValue(ApiRequestStatus.DONE_SUCCESSFUL)
            val responseBody = response.body()!!.translations[0]
            return Pair(responseBody.text, responseBody.detectedLanguageCode)
        } else {
            yandexApiRequestStatus.postValue(ApiRequestStatus.DONE_UNSUCCESSFUL)
            return null
        }

    }
}