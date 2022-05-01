package com.example.notesss.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesss.database.Note
import com.example.notesss.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val notes: LiveData<List<Note>> = repository.getAllNote()

    fun saveNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.addNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(note)
    }

    fun removeAllNote() = viewModelScope.launch(Dispatchers.IO) {
        repository.removeAllNote()
    }

    fun searchNote(query: String): LiveData<List<Note>> {
        return repository.searchNote(query)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
    }

    fun getAllNotes(): LiveData<List<Note>> = repository.getAllNote()

    fun sortByTitle() = repository.sortByTitle()

    fun sortByOldId() = repository.sortByOldId()

    fun onNoteClicked(note: Note) {

    }
}