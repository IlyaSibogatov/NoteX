package com.example.notesss.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM note ORDER BY id ASC")
    fun sortByOldId(): LiveData<List<Note>>

    @Query("SELECT * FROM note ORDER BY id DESC")
    fun sortByNewId(): LiveData<List<Note>>

    @Query("SELECT * FROM Note ORDER BY title ASC")
    fun sortByTitleAsc(): LiveData<List<Note>>

    @Query("SELECT * FROM Note ORDER BY title DESC")
    fun sortByTitleDesc(): LiveData<List<Note>>

    @Query("SELECT * FROM Note WHERE title LIKE :query OR content LIKE :query")
    fun searchNote(query: String): LiveData<List<Note>>

    @Query("DELETE FROM note")
    fun removeAllNote()

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)
}