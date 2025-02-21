package fr.matthsudio.tablesdemultiplication

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.material.color.DynamicColors
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.google.gson.Gson

class FileManager(private var context: Context) {
    fun writeToFile(filename: String, content: String) {
        try {
            // Get the internal storage directory
            val directory = context.filesDir
            // Create a File object for the file you want to create/write to
            val file = File(directory, filename)
            // Create a FileOutputStream to write to the file
            val fileOutputStream = FileOutputStream(file)
            // Write the content to the file as bytes
            fileOutputStream.write(content.toByteArray())
            // Close the FileOutputStream
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the error appropriately (e.g., show an error message)
            throw IOException("Error writing to file: $e")
        }
    }

    fun readFromFile(filename: String): String {
        try {
            // Get the internal storage directory
            val directory = context.filesDir
            // Create a File object for the file you want to read from
            val file = File(directory, filename)
            // Create a FileInputStream to read from the file
            val fileInputStream = file.inputStream()
            // Read the content of the file as bytes
            val bytes = fileInputStream.readBytes()
            // Close the FileInputStream
            fileInputStream.close()
            // Convert the bytes to a String and return it
            return String(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle the error appropriately (e.g., show an error message)
            throw IOException("Error reading from file: $e")
        }
    }

    fun fileExists(context: Context, fileName: String): Boolean {
        // Get the internal storage directory
        val directory: File = context.filesDir

        // Create a File object for the file you want to check
        val file = File(directory, fileName)

        // Check if the file exists
        return file.exists()
    }

    fun deleteFile(context: Context, fileName: String): Boolean {
        // Get the internal storage directory
        val directory: File = context.filesDir

        // Create a File object for the file you want to delete
        val file = File(directory, fileName)

        // Delete the file
        return file.delete()
    }
}

data class Score (
    var averageScore: Double,
    var averageTime: Int,
    var numberOfPart: Long
)
{
    fun saveScore(context: Context) {
        val gson = Gson()
        val fileManager = FileManager(context)

        val json = gson.toJson(this)
        fileManager.writeToFile("score.json", json)
    }
    fun resetScore(context: Context) {
        val fileManager = FileManager(context)
        fileManager.deleteFile(context, "score.json")
        this.averageScore = 0.0
        this.averageTime = 0
        this.numberOfPart = 0
    }
}

class AppStart: Application() {
    private lateinit var fileManager: FileManager
    private lateinit var gson: Gson
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        // init Gson and FileManager
        gson = Gson()
        fileManager = FileManager(this)

        // check if the file exists
        if (!fileManager.fileExists(this, "score.json")) {
            Log.i("AppStart", "score.json does not exist, creating it...")
            // if not, create it
            val score = Score(0.0, 0, 0)
            val json = gson.toJson(score)
            fileManager.writeToFile("score.json", json)
        }

        // read the file
        val json = fileManager.readFromFile("score.json")
        score = gson.fromJson(json, Score::class.java)
        Log.i("AppStart", "score.json read: $score")
    }

    companion object {
        // configuration for debugging
        val tables: MutableList<Int> = mutableListOf(2, 3, 4, 5, 6, 7, 8, 9, 10)
        var questionCount: Int = 3
        lateinit var score: Score
    }
}