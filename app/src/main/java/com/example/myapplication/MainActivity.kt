package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.db.Note
import com.example.myapplication.db.NoteDAO
import com.example.myapplication.db.NoteDatabase
import com.example.myapplication.db.NoteRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

 private lateinit var binding: ActivityMainBinding
    private  lateinit var noteViewModel: NoteViewModel
    lateinit var itemRV : RecyclerView
    lateinit var Button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        itemRV = findViewById(R.id.myRecyclerview)


        val dao : NoteDAO = NoteDatabase.getInstance(application).noteDAO
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory).get(NoteViewModel::class.java)
       binding.myViewModle = noteViewModel
        binding.lifecycleOwner = this
        initRecyclerview()

        noteViewModel.message.observe(this, Observer {
            it.getContentNoHandled()?.let {
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun displayNoteList() {
        noteViewModel.notes.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            binding.myRecyclerview.adapter = NoteAdpater(it,{ selectedItem: Note -> listItemClicked(selectedItem) })
            binding.myRecyclerview.adapter = NoteAdpater(it,{ selectedItem: Note -> onCliked(selectedItem) })
        })
    }

    private fun listItemClicked(note: Note) {
      Toast.makeText(this,"Selected date is ${note.date}",Toast.LENGTH_SHORT).show();
        noteViewModel.initUpdateOrDelete(note)

    }

    private fun initRecyclerview() {
        binding.myRecyclerview.layoutManager = LinearLayoutManager(this)
        displayNoteList()
    }
    private fun onCliked(note: Note) {
        noteViewModel.initUpdateOrDelete(note)
    }

}