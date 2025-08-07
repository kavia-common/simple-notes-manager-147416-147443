package com.example.notesappfrontend.model

import java.util.UUID

// PUBLIC_INTERFACE
data class Note(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var content: String,
    val timestamp: Long = System.currentTimeMillis()
)
