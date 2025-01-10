package fr.matthsudio.tablesdemultiplication

import android.app.Application

class AppStart: Application() {
    companion object {
        var questionRange: IntRange = 2..2
        var questionCount = 13
    }
}