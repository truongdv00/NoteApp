package com.example.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {
    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note_data_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_data_table")
    fun getAllNote():LiveData<List<Note>>
}