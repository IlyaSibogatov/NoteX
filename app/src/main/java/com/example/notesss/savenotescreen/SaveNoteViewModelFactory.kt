package com.example.notesss.savenotescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesss.database.NoteDatabase
import com.example.notesss.database.NoteRepository

class SaveNoteViewModelFactory(private val id: Long) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SaveNoteViewModel(
            id,
            NoteRepository(NoteDatabase.instance.noteDao())
        ) as T
    }
}