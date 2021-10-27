package com.example.myapplication

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.db.Note
import com.example.myapplication.db.NoteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel(), Observable {

    val notes = repository.notes

    @Bindable
    val inputDate = MutableLiveData<String?>()

    @Bindable
    val inputContent = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        val date : String = inputDate.value!!
        val content : String = inputContent.value!!
        insert(Note(0, date, content))
        inputDate.value = null
        inputContent.value = null

    }

    fun clearAllOrDelete() {
        clearAll()
    }

    fun insert(note: Note): Job = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note): Job = viewModelScope.launch {
        repository.update(note)
    }
    fun delete(note: Note): Job = viewModelScope.launch {
        repository.delete(note)
    }
    fun clearAll(): Job = viewModelScope.launch {
        repository.deleteAll()
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}