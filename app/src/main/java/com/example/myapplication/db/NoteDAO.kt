package com.example.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDAO {
    @Insert
    suspend fun insertNote(note: Note) : Long

    @Update
    suspend fun updateNote(note: Note) : Int

    @Delete
    suspend fun deleteNote(note: Note) : Int

    @Query("DELETE FROM note_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM note_data_table")
    fun getAllNote():LiveData<List<Note>>
}