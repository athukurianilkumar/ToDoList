package com.singlepointsolution.todolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/*
Anil Kumar Atukuri
which provides various methods which are used by the application to interact with the user.
They can include a variety of query methods
 */
@Dao
abstract class NoteDao {

    @Upsert
    abstract suspend fun upsertNote(note: Note)

    @Delete
    abstract suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note ORDER BY title ASC")
    abstract fun getNotesOrderdByTitle(): Flow<List<Note>>

    @Query("SELECT * from note WHERE title LIKE :searchQuery")
    abstract fun searchDatabase(searchQuery:String):Flow<List<Note>>
}