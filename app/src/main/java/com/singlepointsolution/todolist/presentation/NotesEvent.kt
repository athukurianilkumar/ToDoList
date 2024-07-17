package com.singlepointsolution.todolist.presentation

import android.util.Log


/*
Anil Kumar Atukuri
This is sealed interface and the subclasses used to deletenote and savenote is used.
 */
// Assuming NotesEvent is a sealed class with a SaveNote object or class
sealed interface NotesEvent {

    data class SaveNote(
        val title: String,
    ) : NotesEvent
}


