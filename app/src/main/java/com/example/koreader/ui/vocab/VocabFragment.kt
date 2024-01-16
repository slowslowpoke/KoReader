package com.example.koreader.ui.vocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.koreader.databinding.FragmentVocabBinding

class VocabFragment : Fragment() {

    private var _binding: FragmentVocabBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vocabViewModel =
            ViewModelProvider(this).get(VocabViewModel::class.java)

        _binding = FragmentVocabBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textVocab
        vocabViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}