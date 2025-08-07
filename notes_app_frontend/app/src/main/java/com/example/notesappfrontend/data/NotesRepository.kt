package com.example.notesappfrontend.data

import com.example.notesappfrontend.model.Note

/**
 * A simple in-memory Notes repository for demonstration purposes.
 * In production, replace this with persistent storage or a database.
 */
// PUBLIC_INTERFACE
object NotesRepository {
    private val notes = mutableListOf<Note>()

    fun getAllNotes(): List<Note> {
        return notes.sortedByDescending { it.timestamp }
    }

    fun getNoteById(id: String): Note? {
        return notes.find { it.id == id }
    }

    fun addNote(note: Note) {
        notes.add(note)
    }

    fun updateNote(note: Note) {
        val index = notes.indexOfFirst { it.id == note.id }
        if (index != -1) {
            notes[index] = note
        }
    }

    fun deleteNoteById(id: String) {
        notes.removeAll { it.id == id }
    }

    fun searchNotes(query: String): List<Note> {
        val lower = query.trim().lowercase()
        return getAllNotes().filter {
            it.title.lowercase().contains(lower) || it.content.lowercase().contains(lower)
        }
    }
}
