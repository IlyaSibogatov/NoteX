package com.example.notesss.homescreen

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesss.R
import com.example.notesss.database.Note
import com.example.notesss.databinding.FragmentNoteBinding
import com.example.notesss.utils.hideKeyboard
import com.example.notesss.viewModel.NoteSortOrder
import com.example.notesss.viewModel.NoteViewModel
import com.example.notesss.viewModel.NotesListRecyclerDiffAdapter
import com.example.notesss.viewModel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_note.*

class HomeScreenFragment : Fragment(R.layout.fragment_note) {

    private lateinit var controller: NavController

    private var _binding: FragmentNoteBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var rvAdapter: NotesListRecyclerDiffAdapter
    private lateinit var noteViewModel: NoteViewModel

    val observer: (List<Note>) -> Unit = { rvAdapter.setList(it) }
    var notesLiveData: LiveData<List<Note>>? = null

    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory()
        noteViewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvAdapter = NotesListRecyclerDiffAdapter(
            layoutInflater,
            object : NotesListRecyclerDiffAdapter.NoteClickListener {
                override fun onNoteClicked(note: Note) {
                    controller.navigate(
                        HomeScreenFragmentDirections
                            .actionHomeScreenToSaveNoteFragment()
                    )
                }
            })
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = Navigation.findNavController(view)
        _binding = FragmentNoteBinding.bind(view)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        initViewModel()

        binding.apply {
            innerFab.setOnClickListener {
                requireView().hideKeyboard()
                controller.navigate(
                    HomeScreenFragmentDirections
                        .actionHomeScreenToSaveNoteFragment()
                )
            }

            search_btn.setOnClickListener {
                requireView().hideKeyboard()
                if (search.text.isNotEmpty()) {
                    noteViewModel.searchNote(search.text.toString())
                        .observe(viewLifecycleOwner, observer)
                }
            }

            delete_search_text.setOnClickListener {
                search.text.clear().also {
                    subscribeToNotes()
                }
            }

            rvNote.adapter = rvAdapter
            rvNote.layoutManager = LinearLayoutManager(requireContext())
        }
        subscribeToNotes()
    }

    private fun subscribeToNotes() {
        notesLiveData?.removeObserver(observer)
        notesLiveData = noteViewModel.notes.also {
            it.observe(viewLifecycleOwner, observer)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_id_first -> noteViewModel.setSortOrder(NoteSortOrder.ASC_ID)
            R.id.old_id_first -> noteViewModel.setSortOrder(NoteSortOrder.DESC_ID)
            R.id.a_z_title -> noteViewModel.setSortOrder(NoteSortOrder.ASC_TITLE)
            R.id.z_a_title -> noteViewModel.setSortOrder(NoteSortOrder.DESC_TITLE)
            R.id.clear_list -> noteViewModel.removeAllNote()
        }
        return true
    }
}