package com.vacral.notepadapply.data.local.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vacral.notepadapply.data.local.local.NoteDao
import com.vacral.notepadapply.data.local.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dao() : NoteDao

    }