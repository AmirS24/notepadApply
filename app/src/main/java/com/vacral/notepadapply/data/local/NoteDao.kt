package com.vacral.notepadapply.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vacral.notepadapply.model.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes (): List<NoteModel>
    @Insert
    fun addNote(noteModel: NoteModel)


}