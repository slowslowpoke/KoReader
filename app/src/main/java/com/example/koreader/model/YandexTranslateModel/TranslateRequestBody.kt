package com.example.koreader.model.YandexTranslateModel

data class TranslateRequestBody(
    val folderId: String,
    val texts: List<String>,
    val targetLanguageCode: String
)