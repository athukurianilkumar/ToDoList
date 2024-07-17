package com.singlepointsolution.todolist.di

import android.content.Context
import androidx.room.Room
import com.singlepointsolution.data.dao.NoteDao
import com.singlepointsolution.data.db.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module // creation of hilt module
// components and scopes will be described later
@InstallIn(SingletonComponent::class)
private object PreferencesDI { // kotlin object is needed

    // third party database
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(
            context, NotesDatabase::class.java, "notes.db"
        ).build()
    }

    @Provides
    fun provideDao(notesDatabase: NotesDatabase): NoteDao {
        return notesDatabase.dao
    }
}