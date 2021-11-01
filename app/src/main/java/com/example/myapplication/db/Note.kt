package com.example.myapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_data_table")
 data class Note(@PrimaryKey(autoGenerate = true)
                 var id: Int,
                 var date: String,
                 var content : String)
