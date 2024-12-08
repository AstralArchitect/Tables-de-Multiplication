package fr.matthsudio.tablesdemultiplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

class Question(one: Int, two: Int){
    var fact1: Int = one
    var fact2: Int = two
    var res: Int = fact1 * fact2
    var answer: Int = 0
}
class Progression{
    private var note: Int = 0
    private var progression: Int = 0
    private var mauvaiseReponses: MutableList<Question> = mutableListOf()
    var cAnswerList: MutableList<Int> = mutableListOf()
    var actualQuestion: Question = Question(0,0)

    fun processQuestion(answer: Int) {
        mauvaiseReponses.add(actualQuestion)
    }
    fun resetMauvaiseReponses() {
        mauvaiseReponses = mutableListOf()
    }
    fun getMauvaiseReponses(): List<Question> {
        return mauvaiseReponses
    }

    fun progress() {
        progression++
    }
    fun resetProgression() {
        progression = 0
    }
    fun getProgression(): Int {
        return progression
    }

    fun getNote(): Int {
        return note
    }
    fun resetNote() {
        note = 0
    }
    fun incNote() {
        this.note++
    }
}

class MainActivity : ComponentActivity() {
    private val userProg = Progression()
    private lateinit var textView: TextView
    private lateinit var nextButton: Button
    private lateinit var restartButton: Button
    private lateinit var answerEditText: EditText

    // changer la plage des tables, actuellement de 2 à 10
    private val range = 2..10

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // init les boutons et autres
        textView = findViewById(R.id.commentText)
        nextButton = findViewById(R.id.nextButton)
        answerEditText = findViewById(R.id.answerEditText)
        restartButton = findViewById(R.id.restartButton)
        // poser une question à l'utilisateur
        val question = ask(range)
        userProg.actualQuestion = question
        answerEditText.hint = "Combien font ${question.fact1} x ${question.fact2} ?"
    }
    @SuppressLint("SetTextI18n")
    fun onButtonClicked(view: View) {
        try {
            val answer = answerEditText.text.toString().toInt()
            userProg.progress()

            if (answer == userProg.actualQuestion.res) {
                userProg.incNote()
            }
            else{
                userProg.actualQuestion.answer = answer
                userProg.processQuestion(answer)
            }
            answerEditText.text.clear()

            if (userProg.getProgression() >= 10) {
                var str = "Vous avez obtenu ${userProg.getNote()}/10\n\nVos erreurs :\n"
                for (i in userProg.getMauvaiseReponses()) {
                    str += "${i.fact1} x ${i.fact2} = ${i.res} (votre réponse : ${i.answer})\n"
                }
                textView.text = str
                nextButton.visibility = View.GONE
                answerEditText.visibility = View.GONE
                restartButton.visibility = View.VISIBLE
                return
            }

            val question = ask(range)
            userProg.actualQuestion = question
            answerEditText.hint = "Combien font ${question.fact1} x ${question.fact2} ?"
            textView.text = ""
        } catch (e: NumberFormatException) {
            textView.text = "Veuillez entrer un nombre"
            return
        }
    }

    private fun ask(range: IntRange): Question {
        var one: Int
        var two: Int

        do {
            one = range.random()
            two = range.random()
        } while (userProg.cAnswerList.contains(one * two))
        userProg.cAnswerList.add(one * two)

        return Question(one, two)
    }

    fun onRestartButtonClicked(view: View) {
        val intent = intent
        finish()
        startActivity(intent)
    }
}

// TODO: changer la couleur du theme
/*fun getThemeColor(context: Context, attribute: Int): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(attribute, typedValue, true)
    return typedValue.data
}
// Usage:
val primaryColor = getThemeColor(this, android.R.attr.colorPrimary)*/