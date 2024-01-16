package com.example.koreader.ui.reader

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.koreader.R
import com.example.koreader.databinding.FragmentReaderBinding

class ReaderFragment : Fragment() {

    private var _binding: FragmentReaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var fullText: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReaderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val tvTextReader: TextView = binding.textReader
        fullText = resources.getString(R.string.sample_text_abstract)
        tvTextReader.text = fullText

        tvTextReader.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val touchPosition = tvTextReader.getOffsetForPosition(event.x, event.y)
                Log.d("ReaderFragment", "X is ${event.x}, Y is ${event.y}, TOUCH POSITION IS  $touchPosition")
                val clickedWord = getWordAtPosition(touchPosition - 1)
                clickedWord?.let {
                    handleWordClick(it)
                }

            }
            true
        }
        return root

    }

    fun getWordAtPosition(position: Int): String? {
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

    private fun handleWordClick(toString: String) {
        Log.d("ReaderFragment", toString)
        Toast.makeText(requireContext(), "You clicked $toString", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


