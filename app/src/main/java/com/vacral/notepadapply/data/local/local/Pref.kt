package com.vacral.notepadapply.data.local.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class Pref (context: Context) {
    val pref: SharedPreferences = context.getSharedPreferences("key", Context.MODE_PRIVATE)

    fun setOnBoardShow() {
        pref.edit {
            putBoolean("show", true)
        }}
        fun setRegister() {
            pref.edit {
                putBoolean("register", true)

            }
        }

        fun isRegister(): Boolean {
            return pref.getBoolean("register", false)
        }

        fun isOnBoardShow(): Boolean {
            return pref.getBoolean("show", false)
        }

    fun clearRegister(){
        pref.edit().clear().apply()
    }
    }