package fr.matthsudio.tablesdemultiplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ConfigurationActivity: AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var questionRangeEditText: EditText
    private lateinit var questionCountEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        button = findViewById(R.id.startButton)
        questionRangeEditText = findViewById(R.id.questionRangeEditText)
        questionCountEditText = findViewById(R.id.questionCountEditText)
    }

    fun onStartButtonClick(view: View) {
        Log.d("ConfigurationActivity", "onStartButtonClick")
        if (questionRangeEditText.text.toString() == "" || questionCountEditText.text.toString() == "")
        {
            Log.d("ConfigurationActivity", "nothing was entered in the editText")
            return
        }
        // get the range from the editText1
        AppStart.questionRange = stringToIntRange(questionRangeEditText.text.toString())
        // get the count from the editText2
        AppStart.questionCount = questionCountEditText.text.toString().toInt()
        Log.d("ConfigurationActivity", "questionRangeEditText: ${questionRangeEditText.text}")
        Log.d("ConfigurationActivity", "questionCountEditText: ${questionCountEditText.text}")

        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    private fun stringToIntRange(range: String): IntRange {
        val parts = range.split("-")
        if (parts.size != 2) {
            return 0..0
        }
        val start = parts[0].toIntOrNull()
        val end = parts[1].toIntOrNull()
        if (start == null || end == null) {
            return 0..0
        }
        return start..end
    }
}