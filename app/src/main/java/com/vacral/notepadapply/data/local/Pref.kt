package com.vacral.notepadapply.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class Pref (context: Context) {
    val pref: SharedPreferences = context.getSharedPreferences("key", Context.MODE_PRIVATE)
    fun saveText(value: String) {
        pref.edit {
            putString("key_one", value)
        }
    }

    fun getText(): String? {
        return pref.getString("key_one", "Text")
    }
    fun setOnBoardShow(){
        pref.edit{
            putBoolean("show", true)
        }
    }
    fun isOnBoardShow(): Boolean{
        return pref.getBoolean("show", false)
    }
}