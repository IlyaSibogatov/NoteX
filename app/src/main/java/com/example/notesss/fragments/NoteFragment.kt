package com.example.notesss.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.notesss.R
import com.example.notesss.activities.MainActivity
import com.example.notesss.databinding.FragmentNoteBinding
import com.example.notesss.utils.hideKeyboard
import com.example.notesss.viewModel.ViewModel
import com.google.android.material.transition.MaterialElevationScale

class NoteFragment : Fragment(R.layout.fragment_note) {

    private lateinit var binding: FragmentNoteBinding
    private val viewModel: ViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(false).apply {
            duration = 350
        }
        enterTransition = MaterialElevationScale(true).apply {
            duration = 350
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteBinding.bind(view)
        val activity = activity as MainActivity
        val controller = Navigation.findNavController(view)
        requireView().hideKeyboard()
        binding.innerFab.setOnClickListener{
            binding.appBarLayout.visibility = View.INVISIBLE
            controller.navigate(NoteFragmentDirections.actionNoteFragmentToSaveNoteFragment())
        }
    }
}