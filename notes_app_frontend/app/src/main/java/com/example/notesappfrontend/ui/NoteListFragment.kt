package com.example.notesappfrontend.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesappfrontend.R
import com.example.notesappfrontend.model.Note
import com.example.notesappfrontend.viewmodel.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer

class NoteListFragment : Fragment() {

    private val viewModel: NotesViewModel by viewModels()
    private lateinit var adapter: NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_notes)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add_note)
        val searchView = view.findViewById<SearchView>(R.id.search_notes)

        adapter = NoteListAdapter(listOf()) { note -> onNoteClicked(note) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            adapter.updateNotes(it)
        })

        fab.setOnClickListener {
            openNoteEditorScreen(null)
        }

        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // ignore submit, treat as text change
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchNotes(newText ?: "")
                return true
            }
        })

        return view
    }

    private fun onNoteClicked(note: Note) {
        openNoteEditorScreen(note)
    }

    private fun openNoteEditorScreen(note: Note?) {
        val fragment = NoteEditorFragment.newInstance(note)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}
