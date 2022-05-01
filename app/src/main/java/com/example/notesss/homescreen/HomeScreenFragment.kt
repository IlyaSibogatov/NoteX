package com.example.notesss.homescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesss.R
import com.example.notesss.database.Note
import com.example.notesss.databinding.FragmentNoteBinding
import com.example.notesss.viewModel.NoteViewModel
import com.example.notesss.viewModel.NotesListRecyclerDiffAdapter
import com.example.notesss.viewModel.ViewModelFactory

class HomeScreenFragment : Fragment(R.layout.fragment_note) {

    private lateinit var controller: NavController

    private var _binding: FragmentNoteBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var rvAdapter: NotesListRecyclerDiffAdapter
    private lateinit var noteViewModel: NoteViewModel

    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory()
        noteViewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvAdapter = NotesListRecyclerDiffAdapter(layoutInflater,
            object : NotesListRecyclerDiffAdapter.NoteClickListener {
                override fun onNoteClicked(note: Note) {
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = Navigation.findNavController(view)
        _binding = FragmentNoteBinding.bind(view)

        initViewModel()


        binding.apply {
            innerFab.setOnClickListener {
                binding.appBarLayout.visibility = View.INVISIBLE
                controller.navigate(HomeScreenFragmentDirections.actionNoteFragmentToSaveNoteFragment())
            }

            sort.setOnClickListener {
                noteViewModel.sortByOldId()
            }

            deleteAll.setOnClickListener {
                noteViewModel.removeAllNote()
            }

            rvNote.adapter = rvAdapter
            rvNote.layoutManager = LinearLayoutManager(requireContext())
        }

        noteViewModel.notes.observe(viewLifecycleOwner) {
            rvAdapter.setList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}