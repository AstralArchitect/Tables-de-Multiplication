package fr.matthsudio.tablesdemultiplication

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

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

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            showResetConfirmationDialog()
        }

        val score = AppStart.score
        averageScoreValue.text = String.format(Locale.getDefault(), "%.2f/20", score.averageScore)
        averageTimeValue.text = String.format(Locale.getDefault(), "%.2f seconds", score.averageTime.toDouble() / 1000.0)
        numberOfPArtsValue.text = String.format(Locale.getDefault(), "%d", score.numberOfPart)

        rotateViewY(averageScoreValue)
        rotateViewY(averageTimeValue)
        rotateViewY(numberOfPArtsValue)

        rotateView(findViewById(R.id.averageScoreLabel))
        rotateView(findViewById(R.id.averageTimeLabel))
        rotateView(findViewById(R.id.bestScoreLabel))
        rotateView(findViewById(R.id.numberOfPartsLabel))

        rotateViewX(findViewById(R.id.resetButton))
        rotateViewX(findViewById(R.id.exitButton))
    }

    private fun showResetConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.reset_stats_title))
        builder.setMessage(getString(R.string.reset_stats_message))

        builder.setPositiveButton(getString(R.string.yes)) { dialog, _: Any ->
            AppStart.score.resetScore(this)
            Toast.makeText(this, getString(R.string.stats_reset), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
        }

        builder.setNegativeButton(getString(R.string.no)) { dialog, _: Any ->
            Toast.makeText(this, getString(R.string.reset_cancelled), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        builder.show()
    }

    private fun rotateViewY(view: View) {
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

    private fun rotateView(view: View) {
        val animator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 180f)
        animator.duration = 500 // Duration in milliseconds (1 second)
        animator.interpolator = LinearInterpolator() // Constant speed
        animator.repeatCount = 1 // Repeat once
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    fun onExitButtonClicked(view: View)
    {
        val intent = Intent(this, ConfigurationActivity::class.java)
        finish()
        startActivity(intent)
    }
}

/*
package fr.matthsudio.tablesdemultiplication

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.matthsudio.tablesdemultiplication.MainActivity
import fr.matthsudio.tablesdemultiplication.R

@Composable
fun ShowResetConfirmationDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = context.getString(R.string.reset_stats_title)) },
        text = { Text(text = context.getString(R.string.reset_stats_message)) },
        confirmButton = {
            TextButton(onClick = {
                AppStart.score.resetScore(context)
                Toast.makeText(context, context.getString(R.string.stats_reset), Toast.LENGTH_SHORT).show()
                onDismiss()
            }) {
                Text(text = context.getString(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                Toast.makeText(context, context.getString(R.string.reset_cancelled), Toast.LENGTH_SHORT).show()
                onDismiss()
            }) {
                Text(text = context.getString(R.string.no))
            }
        }
    )
}

class ScoreAndStats : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var showDialog by remember { mutableStateOf(false) }
            val context = LocalContext.current

            Column(modifier = Modifier.padding(16.dp)) {
                Button(onClick = { showDialog = true }) {
                    Text("Reset Stats")
                }

                if (showDialog) {
                    ShowResetConfirmationDialog(onDismiss = {
                        showDialog = false
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    })
                }
            }
        }
    }

    fun onExiButtonClicked(view: View) {
        val intent = Intent(this, ConfigurationActivity::class.java)
        finish()
        startActivity(intent)
    }
}
 */