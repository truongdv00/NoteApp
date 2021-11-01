package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.db.Note
import com.example.myapplication.db.NoteDAO
import com.example.myapplication.db.NoteDatabase
import com.example.myapplication.db.NoteRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao : NoteDAO = NoteDatabase.getInstance(application).noteDAO
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory).get(NoteViewModel::class.java)
        binding.myViewModle = noteViewModel
        binding.lifecycleOwner = this
        initRecyclerview()
    }
    private fun displayNoteList() {
        noteViewModel.notes.observe(this, Observer {
            binding.myRecyclerview.adapter = NoteAdpater(it) { selectedItem: Note ->
                onCliked(
                    selectedItem
                )
            }
        })
    }
    private fun initRecyclerview() {
        binding.myRecyclerview.layoutManager = LinearLayoutManager(this)
        displayNoteList()
    }
    private fun onCliked(note: Note) {
        noteViewModel.initUpdateOrDelete(note)
    }

}