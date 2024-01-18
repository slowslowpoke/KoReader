package com.example.koreader.ui.vocab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koreader.databinding.FragmentVocabBinding
import com.example.koreader.viewmodel.SharedViewModel

class VocabFragment : Fragment() {

    private var _binding: FragmentVocabBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVocabBinding.inflate(inflater, container, false)

        //RecyclerView
        recyclerView = binding.vocabRecyclerView
        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context,
            LinearLayoutManager.VERTICAL)
        )


        //Иниц. вью модель. По идее она у меня Shared, но хз как пойдет пока...
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        sharedViewModel.wordList.observe(viewLifecycleOwner) { newWordList ->
            adapter.setData(newWordList)
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}