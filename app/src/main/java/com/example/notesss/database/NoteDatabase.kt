package com.example.notesss.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDAO

    companion object {
        lateinit var instance: NoteDatabase


        fun init(applicationContext: Context) {
            instance = Room
                .databaseBuilder(
                    applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).build()
        }
    }
}