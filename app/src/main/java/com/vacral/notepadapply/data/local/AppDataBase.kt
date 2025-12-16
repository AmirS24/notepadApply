package com.vacral.notepadapply.data.local

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.vacral.notepadapply.model.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dao() : NoteDao

    }
