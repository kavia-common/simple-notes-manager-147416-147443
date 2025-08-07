package com.example.notesappfrontend.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappfrontend.R
import com.example.notesappfrontend.model.Note

// PUBLIC_INTERFACE
class NoteListAdapter(
    private var notes: List<Note>,
    private val onItemClicked: (Note) -> Unit
) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {

    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    class NoteViewHolder(view: View, val onItemClicked: (Note) -> Unit) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.card_note)
        val titleText: TextView = view.findViewById(R.id.text_note_title)
        val contentText: TextView = view.findViewById(R.id.text_note_content)
        var currentNote: Note? = null

        init {
            cardView.setOnClickListener {
                currentNote?.let { note -> onItemClicked(note) }
            }
        }

        fun bind(note: Note) {
            currentNote = note
            titleText.text = note.title
            contentText.text = note.content.take(80)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount(): Int = notes.size
}
