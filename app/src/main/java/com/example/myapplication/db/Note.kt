package com.example.myapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_data_table")
 data class Note(@PrimaryKey(autoGenerate = true)
                 val id: Int,
                 val date: String,
                 val content : String)
