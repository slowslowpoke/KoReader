package com.example.koreader.ui.reader

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.koreader.R
import com.example.koreader.databinding.FragmentReaderBinding
import com.example.koreader.model.Word
import com.example.koreader.viewmodel.SharedViewModel

const val TAG = "ReaderFragment"

class ReaderFragment : Fragment() {

    private var _binding: FragmentReaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var fullText: String
    private lateinit var tvTextReader: TextView
    private lateinit var sharedViewModel: SharedViewModel


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
        tvTextReader.setOnTouchListener(CustomTouchListener())
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        return root

    }

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

    inner class CustomTouchListener : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if (event?.action == MotionEvent.ACTION_UP) {
                val touchPosition = tvTextReader.getOffsetForPosition(event.x, event.y)
                val clickedWord = getWordAtPosition(touchPosition - 1)
                clickedWord?.let {
                    handleWordClick(it)
                }
            }
            return true
        }
    }

    private fun handleWordClick(word: String) {
        val newWordOriginal = word.trim { it.isWhitespace() || !it.isLetter() }
        val newWord = Word(0, newWordOriginal, "random translation")

        val alertDialog = AlertDialog.Builder(requireContext())
            .setMessage("Новое слово: ${newWordOriginal.uppercase()}")
            .setPositiveButton("ДОБАВИТЬ") { _, _ ->
                sharedViewModel.addWord(newWord)
                Toast.makeText(
                    requireContext(),
                    "Добавлено в словарь: ${newWordOriginal.uppercase()}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            .setNegativeButton("ОТМЕНА") { _, _ -> }
            .create()

        alertDialog.window?.setBackgroundDrawableResource(R.drawable.new_word_dialog_background)
        alertDialog.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


