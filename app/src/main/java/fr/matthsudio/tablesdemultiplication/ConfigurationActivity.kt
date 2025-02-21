package fr.matthsudio.tablesdemultiplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ConfigurationActivity: AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var questionCountEditText: EditText

    private lateinit var checkBoxes: MutableList<CheckBox>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        button = findViewById(R.id.startButton)
        questionCountEditText = findViewById(R.id.questionCountEditText)

        checkBoxes = mutableListOf()

        checkBoxes.add(findViewById(R.id.checkBox1))
        checkBoxes.add(findViewById(R.id.checkBox2))
        checkBoxes.add(findViewById(R.id.checkBox3))
        checkBoxes.add(findViewById(R.id.checkBox4))
        checkBoxes.add(findViewById(R.id.checkBox5))
        checkBoxes.add(findViewById(R.id.checkBox6))
        checkBoxes.add(findViewById(R.id.checkBox7))
        checkBoxes.add(findViewById(R.id.checkBox8))
        checkBoxes.add(findViewById(R.id.checkBox9))
        checkBoxes.add(findViewById(R.id.checkBox10))
    }

    fun onStartButtonClick(view: View) {
        // get the tables from the checkboxes
        AppStart.tables.clear()
        for (i in 0 until checkBoxes.size)
        {
            if (checkBoxes[i].isChecked)
            {
                AppStart.tables.add(i + 1)
            }
        }

        if (AppStart.tables.isEmpty())
            return
        // get the count from the editText
        if (questionCountEditText.text.toString() == "")
            AppStart.questionCount = 10
        else
            AppStart.questionCount = questionCountEditText.text.toString().toInt()

        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun onScoreButtonClick(view: View) {
        val intent = Intent(this, ScoreAndStats::class.java)
        finish()
        startActivity(intent)
    }
}