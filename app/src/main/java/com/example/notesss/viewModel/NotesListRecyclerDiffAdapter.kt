package com.example.notesss.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesss.database.Note
import com.example.notesss.databinding.NoteItemBinding

class NotesListRecyclerDiffAdapter(
    private val layoutInflater: LayoutInflater,
    private val clickListener: NoteClickListener
) : ListAdapter<Note, NotesListRecyclerDiffAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    fun setList(notes: List<Note>) {
        submitList(notes.toMutableList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        holder.binding.apply {
            noteItemTitle.text = note.title
            noteItemContent.text = note.content
        }
    }

    inner class NoteViewHolder(val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    val note = getItem(layoutPosition)
                    clickListener.onNoteClicked(note)
                }
            }
        }
    }

    interface NoteClickListener {
        fun onNoteClicked(note: Note)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }
}