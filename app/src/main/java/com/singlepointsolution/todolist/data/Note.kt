package com.singlepointsolution.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
Anil Kumar Atukuri
you define entity to represent the objects that you want to store.
 */
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, val title: String
)