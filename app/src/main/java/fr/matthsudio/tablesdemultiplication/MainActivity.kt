package fr.matthsudio.tablesdemultiplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import fr.matthsudio.tablesdemultiplication.ui.theme.TablesDeMultiplicationTheme


var note = 0;
var progression = 0;
var liste = mutableListOf(0);
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
    var prem by remember { mutableStateOf((2..10).random()) }
    var rep by remember { mutableStateOf(TextFieldValue()) }
    var afficherResultat by remember { mutableStateOf(false) }
    var afficherQuestion by remember { mutableStateOf(true) }
    var deux by remember { mutableStateOf((2..10).random()) }
    var resultat by remember { mutableStateOf(prem * deux) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Afficher la question seulement si afficherQuestion est vrai
        if (progression != 10 && afficherQuestion) {
            TextField(
                value = rep,
                onValueChange = { rep = it },
                label = { Text("Combien font $prem x $deux ?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
        if (progression != 10 && afficherQuestion) {
            Button(
                onClick = {
                    if (progression != 10) {
                        if (afficherQuestion) {
                            afficherResultat = true
                            //afficherQuestion = false // Désactiver l'affichage de la question après avoir cliqué sur Valider
                            if (rep.text == resultat.toString()) {
                                note++
                            }
                        }
                        afficherResultat = false
                        afficherQuestion = true
                        deux = (2..10).random()
                        prem = (2..10).random()
                        resultat = prem * deux
                        while (liste.contains(resultat)){
                            deux = (2..10).random()
                            resultat = prem * deux;
                        }
                        liste.add(resultat);
                        rep = TextFieldValue()
                        progression++;
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
        }
        if (progression == 10){
            fin()
        }
    }
}

@Composable
fun fin(modifier: Modifier = Modifier) {
    if (progression == 10) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            if (note <= 6) {
                Surface(color = Color.Unspecified) {
                    Text(
                        text = "Vous avez eu " + note + "/10 ! Il va faloir réviser vos tables de multiplication !",
                        modifier = Modifier.padding(25.dp)
                    )
                }
            }else if (note >= 7 && note != 10) {
                Surface(color = Color.Unspecified) {
                    Text(
                        text = "Vous avez eu " + note + "/10 ! Très bien, mais vous pouvez faire encore mieux.",
                        modifier = Modifier.padding(25.dp)
                    )
                }
            }else if (note == 10) {
                Surface(color = Color.Unspecified) {
                    Text(
                        text = "Vous avez eu " + note + "/10! Exellent, vous êtes un pros des tables de multiplications !",
                        modifier = Modifier.padding(25.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    System.exit(0)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Terminer")
            }
        }
    }
}