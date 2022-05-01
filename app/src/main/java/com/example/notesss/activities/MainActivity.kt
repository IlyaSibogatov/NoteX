package com.example.notesss.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notesss.R
import com.example.notesss.databinding.ActivityMainBinding
import com.example.notesss.homescreen.HomeScreenFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment, HomeScreenFragment(), null)
                .commit()
        }
    }
}