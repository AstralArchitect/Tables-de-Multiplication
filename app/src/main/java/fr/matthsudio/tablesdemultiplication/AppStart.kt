package fr.matthsudio.tablesdemultiplication

import android.app.Application

class AppStart: Application() {
    companion object {
        val tables: MutableList<Int> = mutableListOf<Int>()
        var questionCount = 13
    }
}