package com.singlepointsolution.todolist.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsolution.data.dao.NoteDao
import com.singlepointsolution.data.entities.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
Anil Kumar Atukuri
This is viewmodel class which have all business logic.
 */

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val dao: NoteDao
) : ViewModel() {
    private var notes = dao.getNotesOrderdByTitle()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var searchText = MutableStateFlow("")
    val _state = MutableStateFlow(NoteState())
    val state = combine(_state, notes, searchText) { state, notes, searchText ->
        if (searchText.isEmpty()) {
            state.copy(
                notes = notes
            )
        } else {
            state.copy(notes = notes.filter {
                it.title.contains(searchText, true)
            })
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), NoteState())


    fun SearchText(search: String) {
        searchText.value = search
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.SaveNote -> {
                val note = Note(
                    title = state.value.title.value
                )
                viewModelScope.launch {
                    dao.upsertNote(note)
                }
                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                    )
                }
            }
        }
    }
}

