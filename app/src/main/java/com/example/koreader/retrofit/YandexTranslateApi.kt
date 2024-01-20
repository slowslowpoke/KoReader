package com.example.koreader.retrofit

import com.example.koreader.model.YandexTranslateModel.TranslateRequestBody
import com.example.koreader.model.YandexTranslateModel.TranslateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


const val API_KEY = "AQVNzQVsdvDvlp8Sm71uWLQZwIdHBjShQo6p6PGE"

interface YandexTranslateApi {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Api-Key $API_KEY"
    )
    @POST("translate/v2/translate")
    suspend fun getWordTranslation(@Body translateRequestBody: TranslateRequestBody): Response<TranslateResponse>
}