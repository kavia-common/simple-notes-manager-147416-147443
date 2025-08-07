package com.example.notesappfrontend.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.notesappfrontend.R
import com.example.notesappfrontend.model.Note
import com.example.notesappfrontend.viewmodel.NotesViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NoteEditorFragment : Fragment() {

    private val viewModel: NotesViewModel by viewModels({ requireActivity() })

    private var editingNote: Note? = null

    companion object {
        private const val ARG_NOTE_ID = "note_id"
        private const val ARG_NOTE_TITLE = "note_title"
        private const val ARG_NOTE_CONTENT = "note_content"

        // PUBLIC_INTERFACE
        fun newInstance(note: Note?): NoteEditorFragment {
            if (note == null) {
                return NoteEditorFragment()
            }
            val args = bundleOf(
                ARG_NOTE_ID to note.id,
                ARG_NOTE_TITLE to note.title,
                ARG_NOTE_CONTENT to note.content
            )
            val fragment = NoteEditorFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_note_editor, container, false)

        val editTitle = view.findViewById<EditText>(R.id.edit_note_title)
        val editContent = view.findViewById<EditText>(R.id.edit_note_content)
        val btnSave = view.findViewById<MaterialButton>(R.id.btn_save_note)
        val btnDelete = view.findViewById<ImageButton>(R.id.btn_delete_note)
        btnDelete.visibility = View.GONE

        val noteId = arguments?.getString(ARG_NOTE_ID)
        if (noteId != null) {
            editingNote = viewModel.notes.value?.find { it.id == noteId }
        }
        if (editingNote == null && noteId != null) {
            // Defensive: If not in ViewModel, try repo fetch (if persisted in future).
            // Not found; treat as new note.
        }
        if (editingNote != null) {
            editTitle.setText(editingNote?.title)
            editContent.setText(editingNote?.content)
            btnDelete.visibility = View.VISIBLE
        }

        btnSave.setOnClickListener {
            val title = editTitle.text.toString()
            val content = editContent.text.toString()
            if (title.isBlank() && content.isBlank()) {
                Toast.makeText(requireContext(), R.string.note_empty_error, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (editingNote == null) {
                viewModel.addNote(Note(title = title, content = content))
            } else {
                editingNote?.let {
                    it.title = title
                    it.content = content
                    viewModel.updateNote(it)
                }
            }
            parentFragmentManager.popBackStack()
        }

        btnDelete.setOnClickListener {
            // Confirm deletion
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.delete_note_title)
                .setMessage(R.string.delete_note_message)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.delete) { _, _ ->
                    editingNote?.let {
                        viewModel.deleteNote(it.id)
                    }
                    parentFragmentManager.popBackStack()
                }.show()
        }

        return view
    }
}
