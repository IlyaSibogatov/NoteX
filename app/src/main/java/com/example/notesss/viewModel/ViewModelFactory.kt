package com.example.notesss.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesss.database.NoteDatabase
import com.example.notesss.database.NoteRepository

class ViewModelFactory :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(NoteRepository(NoteDatabase.instance.noteDao())) as T
    }
}