package com.singlepointsolution.todolist.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.singlepointsolution.data.entities.Note

/*
Anil Kumar Atukuri
This is data class with list of note is present
 */
data class NoteState(
    val notes: List<Note> = emptyList(), val title: MutableState<String> = mutableStateOf("")
)