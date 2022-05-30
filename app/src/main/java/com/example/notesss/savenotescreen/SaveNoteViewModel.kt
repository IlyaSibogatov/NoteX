package com.example.notesss.savenotescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesss.database.Note
import com.example.notesss.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaveNoteViewModel(
    id: Long,
    private val repository: NoteRepository
) : ViewModel() {

    var currentNote: LiveData<Note?> = repository.getNoteById(id)


    fun updateOrSaveNote(note: Note) {
        if (note.id == 0L) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.addNote(note)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateNote(note)
            }
        }
    }
}
