package com.example.koreader.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://translate.api.cloud.yandex.net/"
    val api: YandexTranslateApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(YandexTranslateApi::class.java)
    }
}