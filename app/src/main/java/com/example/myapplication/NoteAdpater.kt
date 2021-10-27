package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemNoteBinding
import com.example.myapplication.db.Note

class NoteAdpater(private val listNote : List<Note>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemNoteBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_note, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listNote[position])
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

}
class MyViewHolder(val binding: ItemNoteBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note) {
        binding.txtdate.text = note.date
        binding.txtcontent.text = note.content
    }
}
