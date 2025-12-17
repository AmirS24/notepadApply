package com.vacral.notepadapply.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val desc: String,
    val time: String
)
