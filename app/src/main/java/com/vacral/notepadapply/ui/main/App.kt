package com.vacral.notepadapply.ui.main

import android.app.Application
import androidx.room.Room
import com.vacral.notepadapply.data.local.AppDataBase

public final class App: Application() {
    companion object {
    lateinit var database: AppDataBase
}
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext,
            klass = AppDataBase::class.java,
            "data_base")
            .allowMainThreadQueries()
            .build()

    }
}