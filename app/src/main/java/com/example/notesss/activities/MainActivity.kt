package com.example.notesss.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesss.database.NoteDatabase
import com.example.notesss.databinding.ActivityMainBinding
import com.example.notesss.repository.NoteRepository
import com.example.notesss.viewModel.ViewModel
import com.example.notesss.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try{
            val repository = NoteRepository(NoteDatabase(this))
            val viewModelFactory = ViewModelFactory(repository)
            viewModel = ViewModelProvider(this,viewModelFactory)[ViewModel::class.java]
        }
        catch (ex: Exception){
            Log.d("TAG","ERROR")
        }
    }
}