package com.example.koreader.ui.vocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.koreader.databinding.FragmentVocabBinding
import com.example.koreader.ui.SharedViewModel

class VocabFragment : Fragment() {

    private var _binding: FragmentVocabBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        _binding = FragmentVocabBinding.inflate(inflater, container, false)

        sharedViewModel.wordList.observe(viewLifecycleOwner) { newWordList ->
            //adapter.setData(newWordList)
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}