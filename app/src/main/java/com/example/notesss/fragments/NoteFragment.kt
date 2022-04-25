package com.example.notesss.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notesss.R
import com.example.notesss.activities.MainActivity
import com.example.notesss.databinding.FragmentNoteBinding
import com.example.notesss.viewModel.NoteAdapter
import com.example.notesss.viewModel.ViewModel
import java.util.concurrent.TimeUnit

class NoteFragment : Fragment(R.layout.fragment_note) {

    private lateinit var binding: FragmentNoteBinding
    private val viewModel: ViewModel by activityViewModels()
    private lateinit var rvAdapter: NoteAdapter
    private lateinit var reciclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = Navigation.findNavController(view)
        binding = FragmentNoteBinding.bind(view)
        val activity = activity as MainActivity
        setUpRecyclerView()

        binding.innerFab.setOnClickListener {
            binding.appBarLayout.visibility = View.INVISIBLE
            controller.navigate(NoteFragmentDirections.actionNoteFragmentToSaveNoteFragment())
        }
    }

    private fun setUpRecyclerView() {
        binding.rvNote.apply {
            rvAdapter = NoteAdapter()
            rvAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            adapter = rvAdapter
            postponeEnterTransition(300L, TimeUnit.MILLISECONDS)
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
        observerDataChanges()
    }

    private fun observerDataChanges() {
        viewModel.getAllNotes().observe(viewLifecycleOwner) { list ->
            rvAdapter.setList(list)
        }
    }
}