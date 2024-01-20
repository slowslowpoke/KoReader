package com.example.koreader.ui.reader

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.koreader.R
import com.example.koreader.databinding.FragmentReaderBinding
import com.example.koreader.model.Word
import com.example.koreader.viewmodel.ApiRequestStatus
import com.example.koreader.viewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TAG = "ReaderFragment"

class ReaderFragment : Fragment() {

    private var _binding: FragmentReaderBinding? = null
    private val binding get() = _binding!!
    private lateinit var fullText: String
    private lateinit var tvTextReader: TextView
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var wordToTranslate: String
    private var responseFromYandex: Pair<String, String>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReaderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        tvTextReader = binding.textReader
        fullText = resources.getString(R.string.sample_text_abstract)
        tvTextReader.text = fullText
        tvTextReader.setOnTouchListener(TextTouchListener())
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        Log.d(TAG, "FRAGMENT CREATED -- ${sharedViewModel.yandexApiRequestStatus.value}")
        sharedViewModel.yandexApiRequestStatus.observe(viewLifecycleOwner) { newResponseStatus ->
            processYandexApiResponse(newResponseStatus)
        }
        return root
    }


    inner class TextTouchListener : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if (event?.action == MotionEvent.ACTION_UP) {
                val touchPosition = tvTextReader.getOffsetForPosition(event.x, event.y)
                val clickedWord = getWordAtPosition(touchPosition - 1)
                clickedWord?.let {
                    handleClickedWord(it)
                }
            }
            return true
        }
    }


    //Кажется очень ресурсоемкий способ!!! Пересмотреть
    private fun getWordAtPosition(position: Int): String? {
        val words = fullText.split("\\s+".toRegex())
        var currentStart = 0

        for (word in words) {
            val currentEnd = currentStart + word.length
            if (position in currentStart until currentEnd) {
                // Найдено слово, возвращаем его
                return word
            }
            // Увеличиваем начальную позицию для следующего слова
            currentStart = currentEnd + 1
        }
        // Если символ не принадлежит ни одному слову
        return null
    }


    private fun handleClickedWord(clickedWord: String) {
        wordToTranslate = clickedWord.trim { it.isWhitespace() || !it.isLetter() }

        //Запрос перевода через ретрофит через вьюмодель
        //когда добавлю выбор языка, вместо language code подставлять нужно значение
        binding.progressBar.isVisible = true
        lifecycleScope.launch(Dispatchers.IO) {
            responseFromYandex = getWordTranslation(wordToTranslate)
        }
    }

    private suspend fun getWordTranslation(wordToTranslate: String): Pair<String, String>? {
        return sharedViewModel.getWordTranslation(wordToTranslate)
    }




    private fun processYandexApiResponse(newStatus: ApiRequestStatus) {
        binding.progressBar.isVisible = false
        when (newStatus) {
            ApiRequestStatus.ERROR_NO_INTERNET -> showErrorMessage("Нет подключения к интернету.")
            ApiRequestStatus.ERROR_UNEXPECTED_RESPONSE -> showErrorMessage("Не поддерживаемый ответ от сервера.")
            ApiRequestStatus.DONE_UNSUCCESSFUL -> showErrorMessage("Не удалось получить перевод.")
            ApiRequestStatus.DONE_SUCCESSFUL -> processReceivedTranslation()
            else -> {}
        }

    }

    private fun processReceivedTranslation() {
        val wordOriginal = wordToTranslate
        val wordTranslation = responseFromYandex!!.first
        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage("Новое слово: ${wordOriginal.uppercase()} \n Перевод: ${wordTranslation.uppercase()}")
            .setPositiveButton("ДОБАВИТЬ") { _, _ ->
                sharedViewModel.addWord(Word(0, wordOriginal, wordTranslation))
                sharedViewModel.yandexApiRequestStatus.postValue(ApiRequestStatus.NOTHING_TO_PROCESS)
                Toast.makeText(
                    requireContext(),
                    "Добавлено в словарь: $wordOriginal - $wordTranslation",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "WORD ADDED -- ${sharedViewModel.yandexApiRequestStatus.value}")
            }
            .setNegativeButton("ОТМЕНА") { _, _ -> }
            .create()
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.new_word_dialog_background)
        alertDialog.show()

    }

    private fun showErrorMessage(errorMessage: String) {
        Toast.makeText(
            requireContext(),
            errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


