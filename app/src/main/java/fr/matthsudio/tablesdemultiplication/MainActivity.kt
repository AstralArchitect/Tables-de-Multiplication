package fr.matthsudio.tablesdemultiplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.matthsudio.tablesdemultiplication.ui.theme.TablesDeMultiplicationTheme
import kotlinx.coroutines.delay

var note = 0;
var prem = (2..10).random()
var deux = (2..10).random()
var resultat = prem * deux
var a = 1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TablesDeMultiplicationTheme {
                // Une surface en utilisant la couleur de fond du thème
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (a == 1){
                        PoserQuestion(note)
                        a = 0
                    }
                }
            }
        }
    }
}

@Composable
fun PoserQuestion(note: Int) {
    var rep by remember { mutableStateOf(TextFieldValue()) }
    var afficherResultat by remember { mutableStateOf(false) }
    var afficherQuestion by remember { mutableStateOf(true) }
    afficherQuestion = false
    afficherQuestion = true

    for (i in 0..9) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Afficher la question seulement si afficherQuestion est vrai
            TextField(
                value = rep,
                onValueChange = { rep = it },
                label = { Text("Combien font $prem x $deux ?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    if (afficherQuestion) {
                        afficherResultat = true
                        afficherQuestion = false
                    } else {
                        afficherQuestion = true
                        afficherResultat = false
                        prem = (2..10).random()
                        deux = (2..10).random()
                        resultat = prem * deux
                        rep = TextFieldValue()
                    }
                    afficherQuestion = !afficherQuestion
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                if (afficherQuestion) {
                    Text("Valider")
                } else {
                    Text("Recommencer")
                }
            }


            if (afficherResultat) {
                VerifierQuestion(rep.text, resultat, note)
            }
        }
    }
}

@Composable
fun VerifierQuestion(rep: String, resultat: Int, note: Int, modifier: Modifier = Modifier) {
    val reponse: Int = rep.toInt()
    var notes = note;

    if (reponse == resultat) {
        notes = note + 1;
        Surface(color = Color.Unspecified) {
            Text(
                text = "Bravo!" + " vous êtes à " + notes + " bonnes réponses",
                modifier = Modifier.padding(25.dp)
            )
        }
    }else{
        Surface(color = Color.Unspecified) {
            Text(
                text = "Mauvaise réponse," + " vous êtes à " + notes + " bonnes réponses",
                modifier = Modifier.padding(25.dp)
            )
        }
    }
}
