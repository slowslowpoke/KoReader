package com.example.koreader.ui.vocab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.koreader.databinding.VocabWordItemBinding
import com.example.koreader.model.Word

class WordListAdapter: RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {
    private var mWordList = emptyList<Word>()

        class WordViewHolder(private val binding: VocabWordItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(word: Word, position: Int){
                binding.apply {
                    tvNumber.text = position.toString()
                    tvWordOriginal.text = word.original
                    tvWordTranslated.text = word.translated
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = VocabWordItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return WordViewHolder(binding)
    }

    override fun getItemCount() = mWordList.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentWord = mWordList[position]
        holder.bind(currentWord, position + 1)
    }

    fun setData(newWordList: List<Word>){
        this.mWordList = newWordList
        notifyDataSetChanged()

    }

}