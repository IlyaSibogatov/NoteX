package com.example.notesss

import android.app.Application
import com.example.notesss.database.NoteDatabase

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        NoteDatabase.init(this)
    }

    companion object {
        lateinit var instance: Application
    }
}