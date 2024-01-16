package com.example.koreader.ui.bookshelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.koreader.databinding.FragmentBookshelfBinding

class BookshelfFragment : Fragment() {

    private var _binding: FragmentBookshelfBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bookshelfViewModel =
            ViewModelProvider(this).get(BookshelfViewModel::class.java)

        _binding = FragmentBookshelfBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBookshelf
        bookshelfViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}