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
import com.example.notesss.RecyclerViewAdapter.NotesListRecyclerDiffAdapter
import com.example.notesss.database.Note
import com.example.notesss.databinding.FragmentNoteBinding
import com.example.notesss.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_note.*

class HomeScreenFragment : Fragment(R.layout.fragment_note) {

    private lateinit var rvAdapter: NotesListRecyclerDiffAdapter
    private lateinit var viewModel: HomeScreenNoteViewModel
    private lateinit var controller: NavController

    private var _binding: FragmentNoteBinding? = null
    private val binding
        get() = _binding!!

    val observer: (List<Note>) -> Unit = { rvAdapter.setList(it) }
    var notesLiveData: LiveData<List<Note>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvAdapter = NotesListRecyclerDiffAdapter(
            layoutInflater,
            object : NotesListRecyclerDiffAdapter.NoteClickListener {
                override fun onNoteClicked(note: Note) {
                    val bundle = Bundle()
                    bundle.putLong(ARGUMENT_ID, note.id)
                    controller.navigate(
                        R.id.action_homeScreen_to_saveNoteFragment,
                        bundle
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
                    viewModel.searchNote(search.text.toString())
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
        notesLiveData = viewModel.notes.also {
            it.observe(viewLifecycleOwner, observer)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel() {
        val viewModelFactory = HomeScreenViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeScreenNoteViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_id_first -> viewModel.setSortOrder(NoteSortOrder.ASC_ID)
            R.id.old_id_first -> viewModel.setSortOrder(NoteSortOrder.DESC_ID)
            R.id.a_z_title -> viewModel.setSortOrder(NoteSortOrder.ASC_TITLE)
            R.id.z_a_title -> viewModel.setSortOrder(NoteSortOrder.DESC_TITLE)
            R.id.clear_list -> viewModel.removeAllNote()
        }
        return true
    }

    companion object {
        private const val ARGUMENT_ID = "ARGUMENT_ID"
    }
}