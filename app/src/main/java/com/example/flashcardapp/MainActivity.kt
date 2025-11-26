package com.example.flashcardapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.Activity
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashcardAnswer   = findViewById<TextView>(R.id.flashcard_answer)
        val buttonAdd         = findViewById<Button>(R.id.button_add)

        // --- Logique d'affichage question/réponse ---
        flashcardQuestion.setOnClickListener {
            flashcardQuestion.visibility = View.INVISIBLE
            flashcardAnswer.visibility = View.VISIBLE
        }

        flashcardAnswer.setOnClickListener {
            flashcardQuestion.visibility = View.VISIBLE
            flashcardAnswer.visibility = View.INVISIBLE
        }

        // --- Gestion du retour de AddCard ---
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                val question = data.getStringExtra("question")
                val answer   = data.getStringExtra("answer")

                // Mettre à jour la carte avec les nouvelles données
                flashcardQuestion.text = question
                flashcardAnswer.text   = answer
            } else {
                Log.i("AddCardActivity", "Opération annulée ou pas de données")
            }
        }

        // --- Bouton pour ajouter/éditer une carte ---
        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddCard::class.java)

            // On passe la question/réponse actuelle pour édition
            intent.putExtra("question", flashcardQuestion.text.toString())
            intent.putExtra("answer", flashcardAnswer.text.toString())

            resultLauncher.launch(intent)
        }
    }
}