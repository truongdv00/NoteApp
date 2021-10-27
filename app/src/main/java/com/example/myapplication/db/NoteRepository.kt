package com.example.myapplication.db

class NoteRepository(private val dao : NoteDAO) {

    val notes = dao.getAllNote()

    suspend fun insert(note: Note) {
        dao.insertNote(note)
    }
    suspend fun update(note: Note) {
        dao.updateNote(note)
    }
    suspend fun delete(note: Note) {
        dao.deleteNote(note)
    }
    suspend fun deleteAll() {
        dao.deleteAll()
    }
}