package com.example.myapplication

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.Note
import com.example.myapplication.db.NoteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel(), Observable {

    val notes = repository.notes
    private var isUpdateOrDelete = false
    private lateinit var noteToUpdateOrDelete: Note

    @Bindable
    val inputDate = MutableLiveData<String?>()

    @Bindable
    val inputContent = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    @Bindable
    val relative = MutableLiveData<String?>()


    private val statsManager = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statsManager


    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear"
    }

    fun saveOrUpdate() {

        if (isUpdateOrDelete) {
            noteToUpdateOrDelete.date = inputDate.value!!
            noteToUpdateOrDelete.content = inputContent.value!!
            update(noteToUpdateOrDelete)
        } else {
            val date: String = inputDate.value!!
            val content: String = inputContent.value!!
            insert(Note(0, date, content))
            inputDate.value = null
            inputContent.value = null
        }

    }

    fun plus() {
        relative
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(noteToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(note: Note): Job = viewModelScope.launch {
        val newDate: Long = repository.insert(note)
        if (newDate > -1) {
            statsManager.value = Event("Subscriber Inserted successfully $newDate")
        } else {
            statsManager.value = Event("Error Occurred")
        }
    }

    fun update(note: Note): Job = viewModelScope.launch {
        val noOfDate: Int = repository.update(note)
        if (noOfDate > 0) {
            repository.update(note)
            inputDate.value = null
            inputContent.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear"
            statsManager.value = Event("$noOfDate Row Update successfully")
        } else {

            statsManager.value = Event("Error Occurred")
        }
    }

    fun delete(note: Note): Job = viewModelScope.launch {
        repository.delete(note)
        inputDate.value = null
        inputContent.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear"
        statsManager.value = Event("Subscriber Delete successfully")

    }

    fun clearAll(): Job = viewModelScope.launch {
        repository.deleteAll()
        statsManager.value = Event("All Subscriber Delete successfully")
    }

    fun initUpdateOrDelete(note: Note) {
        inputDate.value = note.date
        inputContent.value = note.content
        isUpdateOrDelete = true
        noteToUpdateOrDelete = note
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}