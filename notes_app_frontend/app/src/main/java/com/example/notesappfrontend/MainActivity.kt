package com.example.notesappfrontend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.notesappfrontend.ui.NoteListFragment

class MainActivity : AppCompatActivity() {
    /**
     * Entry point for the app. Loads the NoteListFragment.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, NoteListFragment())
            }
        }
    }
}
