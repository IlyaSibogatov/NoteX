package com.example.notesss.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.notesss.R
import com.example.notesss.activities.MainActivity
import com.example.notesss.databinding.FragmentSaveNoteBinding
import com.example.notesss.model.Note
import com.example.notesss.utils.hideKeyboard
import com.example.notesss.viewModel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class SaveNoteFragment : Fragment(R.layout.fragment_save_note) {

    private lateinit var controller: NavController
    private lateinit var binding: FragmentSaveNoteBinding
    private var note: Note? = null
    private val viewModel: ViewModel by activityViewModels()
    private val coroutine = CoroutineScope(Dispatchers.Main)
    private val args: SaveNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSaveNoteBinding.bind(view)

        controller = Navigation.findNavController(view)
        val activity = activity as MainActivity

        binding.bckBtn.setOnClickListener {
            requireView().hideKeyboard()
            controller.popBackStack()
        }

        binding.saveNote.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        if (binding.etTitle.text.toString().isEmpty() ||
            binding.etNoteContent.text.toString().isEmpty()
        ) {
            Toast.makeText(activity, "Something is empty", Toast.LENGTH_SHORT).show()
        } else {
            note = args.note
            when (note) {
                null -> {
                    viewModel.saveNote(
                        Note
                            (
                            0,
                            binding.etTitle.text.toString(),
                            binding.etNoteContent.text.toString()
                        )
                    )

                    controller.navigate(SaveNoteFragmentDirections.actionSaveNoteFragmentToNoteFragment())
                }
                else -> {
                    //update
                }
            }
        }
    }
}