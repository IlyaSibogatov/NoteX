package com.example.notesss.database

class NoteRepository(private val noteDao: NoteDAO) {

    fun getAllNote() = noteDao.sortByNewId()
    fun sortByOldId() = noteDao.sortByOldId()
    fun sortByNewId() = noteDao.sortByNewId()
    fun removeAllNote() = noteDao.removeAllNote()
    fun sortByTitleAsc() = noteDao.sortByTitleAsc()
    fun sortByTitleDesc() = noteDao.sortByTitleDesc()
    fun getNoteById(id: Long) = noteDao.getNoteById(id)
    fun searchNote(query: String) = noteDao.searchNote(query)
    suspend fun addNote(note: Note) = noteDao.addNote(note)
    suspend fun updateNote(note: Note) = noteDao.updateNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
}