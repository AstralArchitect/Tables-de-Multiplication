package fr.matthsudio.tablesdemultiplication

import android.app.Application

class AppStart: Application() {
    companion object {
        // configuration for debugging
        val tables: MutableList<Int> = mutableListOf<Int>(2, 3, 4, 5, 6, 7, 8, 9, 10)
        var questionCount: Int = 3
    }
}