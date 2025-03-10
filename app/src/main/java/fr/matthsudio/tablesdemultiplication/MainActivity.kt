package fr.matthsudio.tablesdemultiplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class Question(one: Int, two: Int){
    var table: Int = one
    var question: Int = two
    var res: Int = table * question
    var answer: Int = 0
}
class Progression{
    private var note: Int = 0
    private var progression: Int = 0
    private var mauvaiseReponses: MutableList<Question> = mutableListOf()
    var cAnswerList: MutableList<Int> = mutableListOf()
    var actualQuestion: Question = Question(0,0)

    fun processQuestion() {
        mauvaiseReponses.add(actualQuestion)
    }
    fun getMauvaiseReponses(): List<Question> {
        return mauvaiseReponses
    }

    fun progress() {
        progression++
    }
    fun getProgression(): Int {
        return progression
    }

    fun getNote(): Int {
        return note
    }
    fun incNote() {
        this.note++
    }
}

class MainActivity : AppCompatActivity() {
    private val userProg = Progression()
    private lateinit var textView: TextView
    private lateinit var nextButton: Button
    private lateinit var restartButton: Button
    private lateinit var quitButton: Button
    private lateinit var answerEditText: EditText
    private lateinit var returnButton: Button
    private lateinit var chronometer: Chronometer
    private var elapsedTime: Long = 0
    private var isRunning = false

    // definir la plage des tables
    private val tables = AppStart.tables
    private val questionCount = AppStart.questionCount
    private val questionRange = 2..12

    companion object {
        private const val ELAPSED_TIME_KEY = "elapsed_time"
        private const val IS_RUNNING_KEY = "is_running"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // init les boutons et autres widgets
        textView = findViewById(R.id.commentText)
        nextButton = findViewById(R.id.nextButton)
        answerEditText = findViewById(R.id.answerEditText)
        restartButton = findViewById(R.id.restartButton)
        quitButton = findViewById(R.id.quitButton)
        returnButton = findViewById(R.id.returnButton)
        chronometer = findViewById(R.id.chronometer)

        if (savedInstanceState != null) {
            elapsedTime = savedInstanceState.getLong(ELAPSED_TIME_KEY)
            isRunning = savedInstanceState.getBoolean(IS_RUNNING_KEY)
            chronometer.base = SystemClock.elapsedRealtime() - elapsedTime
            if (isRunning) {
                chronometer.start()
            }
        } else {
            chronometer.base = SystemClock.elapsedRealtime()
        }

        // set the answer listener (when enter is pressed)
        answerEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Call your function here
                onButtonClicked(nextButton)
                true
            } else {
                false
            }
        }
        // poser une question à l'utilisateur
        val question = ask(tables)
        userProg.actualQuestion = question
        answerEditText.hint = "Combien font ${question.table} x ${question.question} ?"
        chronometer.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        elapsedTime = SystemClock.elapsedRealtime() - chronometer.base
        outState.putLong(ELAPSED_TIME_KEY, elapsedTime)
        outState.putBoolean(IS_RUNNING_KEY, isRunning)
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    fun onButtonClicked(view: View) {
        try {
            val answer = answerEditText.text.toString().toInt()
            userProg.progress()

            if (answer == userProg.actualQuestion.res) {
                userProg.incNote()
            }
            else{
                userProg.actualQuestion.answer = answer
                userProg.processQuestion()
            }
            answerEditText.text.clear()

            if (userProg.getProgression() >= questionCount) {
                var str = "Vous avez eu " + String.format("%.2f", userProg.getNote().toFloat() / questionCount.toFloat() * 20.0) + "/20 en " + getFormattedElapsedTime(chronometer) + " secondes."

                str += if ((userProg.getNote().toFloat() / questionCount.toFloat()).toDouble() != 1.0) "\nTemps moyen par question : ${getFormattedElapsedTime(chronometer, questionCount)}\n\nVos erreurs :\n"
                else ", bravo!\n\nTemps moyen par question : ${getFormattedElapsedTime(chronometer, questionCount)}"

                for (i in userProg.getMauvaiseReponses()) {
                    str += "${i.table} x ${i.question} = ${i.res} (votre réponse : ${i.answer})\n"
                }
                textView.text= str
                view.visibility = View.GONE
                answerEditText.visibility = View.GONE
                restartButton.visibility = View.VISIBLE
                quitButton.visibility = View.VISIBLE
                returnButton.visibility = View.GONE
                chronometer.stop()
                chronometer.visibility = View.GONE
                answerEditText.isEnabled = false


                // update score
                val score = AppStart.score
                score.averageScore = ((score.numberOfPart * score.averageScore) + userProg.getNote().toFloat() / questionCount.toFloat() * 20.0) / (score.numberOfPart + 1)
                score.averageTime = (((score.numberOfPart * score.averageTime) + (SystemClock.elapsedRealtime() - chronometer.base) / questionCount) / (score.numberOfPart + 1)).toInt()
                score.numberOfPart++

                score.saveScore(this)

                return
            }

            val question = ask(tables)
            userProg.actualQuestion = question
            answerEditText.hint = "Combien font ${question.table} x ${question.question} ?"
            textView.text = ""
        } catch (e: NumberFormatException) {
            textView.text = "Veuillez entrer un nombre"
            return
        }
    }

    private fun ask(range: MutableList<Int>): Question {
        var one: Int
        var two: Int

        val maxSize = range.count() * questionRange.count()

        if (userProg.cAnswerList.size >= maxSize)
            userProg.cAnswerList.clear()

        Log.i("MainActivity:ask()", "range: $range")
        Log.i("MainActivity:ask()", "userProg.cAnswerList's size: ${userProg.cAnswerList.size}")

        do {
            one = range.random()
            two = questionRange.random()
        } while (userProg.cAnswerList.contains(one * two))
        userProg.cAnswerList.add(one * two)

        return Question(one, two)
    }

    private fun getFormattedElapsedTime(chronometer: Chronometer, qCount: Int = 1): String {
        val elapsedMillis = (SystemClock.elapsedRealtime() - chronometer.base) / qCount
        val centiseconds = elapsedMillis / 10 // Convert milliseconds to centiseconds
        return formatTime(centiseconds.toInt())
    }

    private fun formatTime(centiseconds: Int): String {
        val minutes = centiseconds / 6000
        val seconds = (centiseconds % 6000) / 100
        val hundredths = centiseconds % 100

        val minutesFormat = DecimalFormat("00")
        val secondsFormat = DecimalFormat("00")
        val hundredthsFormat = DecimalFormat("00")

        return "${minutesFormat.format(minutes)}:${secondsFormat.format(seconds)}:${hundredthsFormat.format(hundredths)}"
    }

    fun onRestartButtonClicked(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun onQuitButtonClicked(view: View) {
        val intent = Intent(this, ConfigurationActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun onReturnButtonClicked(view: View) {
        finish()
    }
}