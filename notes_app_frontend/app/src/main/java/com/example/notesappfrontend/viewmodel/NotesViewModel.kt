package com.example.notesappfrontend.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notesappfrontend.data.NotesRepository
import com.example.notesappfrontend.model.Note

// PUBLIC_INTERFACE
class NotesViewModel : ViewModel() {
    private val _notes = MutableLiveData<List<Note>>(NotesRepository.getAllNotes())
    val notes: LiveData<List<Note>> get() = _notes

    // For search function
    private val _searchQuery = MutableLiveData("")

    fun refreshNotes() {
        _notes.value = NotesRepository.getAllNotes()
    }

    fun searchNotes(query: String) {
        _searchQuery.value = query
        _notes.value = if (query.isBlank()) {
            NotesRepository.getAllNotes()
        } else {
            NotesRepository.searchNotes(query)
        }
    }

    fun addNote(note: Note) {
        NotesRepository.addNote(note)
        refreshNotes()
    }

    fun updateNote(note: Note) {
        NotesRepository.updateNote(note)
        refreshNotes()
    }

    fun deleteNote(id: String) {
        NotesRepository.deleteNoteById(id)
        refreshNotes()
    }
}
