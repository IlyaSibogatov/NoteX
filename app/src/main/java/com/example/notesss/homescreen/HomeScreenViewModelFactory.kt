package com.example.notesss.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesss.database.NoteDatabase
import com.example.notesss.database.NoteRepository

class HomeScreenViewModelFactory :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreenNoteViewModel(NoteRepository(NoteDatabase.instance.noteDao())) as T
    }
}