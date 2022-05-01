package com.example.notesss.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM note")
    fun getAllNote(): LiveData<List<Note>>

    @Query("SELECT * FROM Note ORDER BY id ASC")
    fun sortByOldId(): LiveData<List<Note>>

    @Query("SELECT * FROM Note ORDER BY title DESC")
    fun sortByTitle(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE title LIKE :query OR content LIKE :query")
    fun searchNote(query: String): LiveData<List<Note>>

    @Query("DELETE FROM note")
    fun removeAllNote()

    @Delete
    suspend fun deleteNote(note: Note)
}