package com.vacral.notepadapply.data.local.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vacral.notepadapply.data.local.model.NoteModel

@Dao
interface NoteDao {
//    @Query("SELECT * FROM notes ORDER BY id DESC")
//    fun getAllNotes(): List<NoteModel>
    @Query("SELECT * FROM notes WHERE title LIKE :searchText||'%' ORDER BY id DESC")
    fun searchByTitle(searchText: String? = ""): List<NoteModel>

    @Insert
    fun addNote(noteModel: NoteModel)

    @Delete
    fun deleteNote(noteModel: NoteModel)

    @Update
    fun updateNote(noteModel: NoteModel)
}