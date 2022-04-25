package com.example.notesss.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesss.model.Note
import com.example.notesss.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

class ViewModel(private val repository: NoteRepository) : ViewModel() {

    fun saveNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.addNote(note)
    }
    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.updateNote(note)
    }
    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteNote(note)
    }
    fun searchNote(query: String): LiveData<List<Note>>
    {
    return repository.searchNote(query)
    }
    fun getAllNotes(): LiveData<List<Note>> = repository.getNote()
}