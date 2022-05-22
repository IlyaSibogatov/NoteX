package com.example.notesss.savenotescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.notesss.R
import com.example.notesss.database.Note
import com.example.notesss.databinding.FragmentSaveNoteBinding
import com.example.notesss.utils.hideKeyboard
import com.example.notesss.viewModel.NoteViewModel
import com.example.notesss.viewModel.ViewModelFactory

class SaveNoteFragment : Fragment(R.layout.fragment_save_note) {

    private lateinit var controller: NavController
    private var _binding: FragmentSaveNoteBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel

    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory()
        noteViewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = Navigation.findNavController(view)
        _binding = FragmentSaveNoteBinding.bind(view)

        initViewModel()

        binding.apply {
            binding.bckBtn.setOnClickListener {
                requireView().hideKeyboard()
                controller.popBackStack()
            }

            binding.saveNote.setOnClickListener {
                noteViewModel.saveNote(
                    Note(
                        0,
                        binding.etTitle.text.toString(),
                        binding.etNoteContent.text.toString()
                    )
                )
                controller.popBackStack()
            }
        }
    }
}