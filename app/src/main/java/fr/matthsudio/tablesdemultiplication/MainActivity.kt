package fr.matthsudio.tablesdemultiplication

import android.os.Bundle
import android.os.Environment
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
import androidx.core.content.ContentProviderCompat.requireContext
import fr.matthsudio.tablesdemultiplication.ui.theme.TablesDeMultiplicationTheme
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter

var note = 0;
var a = 1;

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
                    PoserQuestion()
                }
            }
        }
    }
}

@Composable
fun PoserQuestion() {
    var rep by remember { mutableStateOf(TextFieldValue()) }
    var afficherResultat by remember { mutableStateOf(false) }
    var afficherQuestion by remember { mutableStateOf(true) }
    var prem by remember { mutableStateOf((2..10).random()) }
    var deux by remember { mutableStateOf((2..10).random()) }
    var resultat by remember { mutableStateOf(prem * deux) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Afficher la question seulement si afficherQuestion est vrai
        if (a != 10 && afficherQuestion) {
            TextField(
                value = rep,
                onValueChange = { rep = it },
                label = { Text("Combien font $prem x $deux ?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
        Button(
            onClick = {
                if (a != 10) {
                    if (afficherQuestion) {
                        afficherResultat = true
                        afficherQuestion = false // Désactiver l'affichage de la question après avoir cliqué sur Valider
                        if (rep.text == resultat.toString()) {
                            note++
                        }
                    }
                    afficherResultat = false
                    afficherQuestion = true
                    prem = (2..10).random()
                    deux = (2..10).random()
                    resultat = prem * deux
                    rep = TextFieldValue()
                    a++
                } else {
                    afficherResultat = false
                    afficherQuestion = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            if (afficherQuestion) {
                Text("Valider")
            }
        }
        if (a == 10){
            fin()
        }
    }
}

@Composable
fun fin(modifier: Modifier = Modifier) {
    if (a == 10) {
        Surface(color = Color.Unspecified) {
            Text(
                text = "Vous avez eu " + note + "/10 ! Vous pouvez fermer l'application.",
                modifier = Modifier.padding(25.dp)
            )
        }
    }

}


