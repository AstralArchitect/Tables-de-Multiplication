package fr.matthsudio.tablesdemultiplication

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreAndStats : AppCompatActivity() {
    private lateinit var averageScoreValue: TextView
    private lateinit var averageTimeValue: TextView
    private lateinit var numberOfPArtsValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_and_stats)

        averageScoreValue = findViewById(R.id.averageScoreValue)
        averageTimeValue = findViewById(R.id.averageTimeValue)
        numberOfPArtsValue = findViewById(R.id.numberOfPartsValue)

        val score = AppStart.score
        averageScoreValue.text = score.averageScore.toString() + "/20"
        averageTimeValue.text = score.averageTime.toString() + " seconds"
        numberOfPArtsValue.text = score.numberOfPart.toString()

        rotateView(findViewById(R.id.bestScoreLabel))
        rotateView(findViewById(R.id.averageScoreLabel))
        rotateView(findViewById(R.id.averageTimeLabel))
        rotateView(findViewById(R.id.numberOfPartsLabel))

        rotateViewX(findViewById(R.id.exitButton))
        rotateViewX(findViewById(R.id.resetButton))
    }

    private fun rotateView(view: View) {
        val animator = ObjectAnimator.ofFloat(view, View.ROTATION_Y, 0f, 180f)
        animator.duration = 500 // Duration in milliseconds (1 second)
        animator.interpolator = LinearInterpolator() // Constant speed
        animator.repeatCount = 1 // Repeat once
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    private fun rotateViewX(view: View) {
        val animator = ObjectAnimator.ofFloat(view, View.ROTATION_X, 0f, 180f)
        animator.duration = 500 // Duration in milliseconds (1 second)
        animator.interpolator = LinearInterpolator() // Constant speed
        animator.repeatCount = 1 // Repeat once
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    fun onExiButtonClicked(view: View)
    {
        val intent = Intent(this, ConfigurationActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun onResetButtonClicked(view: View) {
        val score = AppStart.score
        score.resetScore(this)
        val intent = Intent(this, ConfigurationActivity::class.java)
        finish()
        startActivity(intent)
    }
}