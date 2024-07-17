package com.singlepointsolution.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase

/*
Anil Kumar Atukuri
The actual application’s database, which is the main access point to the application’s persisted data
 */
@Database(
    entities = [Note::class], version = 1
)
abstract class NotesDatabase : RoomDatabase() {
    abstract val dao: NoteDao
}