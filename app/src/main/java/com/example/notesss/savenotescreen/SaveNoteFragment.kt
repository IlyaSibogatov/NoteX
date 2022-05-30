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

class SaveNoteFragment : Fragment(R.layout.fragment_save_note) {

    private lateinit var viewModel: SaveNoteViewModel
    private lateinit var controller: NavController

    private var _binding: FragmentSaveNoteBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = Navigation.findNavController(view)
        _binding = FragmentSaveNoteBinding.bind(view)

        val id = arguments?.getLong(ARGUMENT_ID) ?: INVALID_ID
        initViewModel(id)

        binding.apply {
            bckBtn.setOnClickListener {
                requireView().hideKeyboard()
                controller.popBackStack()
            }
            saveNote.setOnClickListener {
                viewModel.updateOrSaveNote(
                    Note(
                        id,
                        etTitle.text.toString(),
                        etNoteContent.text.toString()
                    )
                )
                controller.popBackStack()
            }
        }
        viewModel.currentNote.observe(viewLifecycleOwner) {
            binding.etTitle.setText(it?.title)
            binding.etNoteContent.setText(it?.content)
        }
    }

    private fun initViewModel(id: Long) {
        val viewModelFactory = SaveNoteViewModelFactory(id)
        viewModel = ViewModelProvider(this, viewModelFactory)[SaveNoteViewModel::class.java]
    }

    companion object {
        private const val ARGUMENT_ID = "ARGUMENT_ID"
        private const val INVALID_ID = -1L
    }
}