package com.singlepointsolution.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.singlepointsolution.data.entities.Note
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

    @Query("SELECT * FROM note ORDER BY title ASC")
    abstract fun getNotesOrderdByTitle(): Flow<List<Note>>


}