package com.example.flashcardapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_card)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextQuestion = findViewById<EditText>(R.id.edit_text_question)
        val editTextAnswer   = findViewById<EditText>(R.id.edit_text_answer)
        val buttonSave       = findViewById<Button>(R.id.button_save)
        val buttonCancel     = findViewById<Button>(R.id.button_cancel)

        // --- Bouton Annuler ---
        buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED) // on indique que l’opération est annulée
            finish() // on ferme simplement l’activité
        }

        // --- Bouton Sauvegarder ---
        buttonSave.setOnClickListener {
            val question = editTextQuestion.text.toString().trim()
            val answer   = editTextAnswer.text.toString().trim()

            if (question.isBlank() || answer.isBlank()) {
                val toast = Toast.makeText(applicationContext, "Tous les champs sont requis !!!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                return@setOnClickListener
            }

            val resultIntent = Intent()
            resultIntent.putExtra("question", question)
            resultIntent.putExtra("answer", answer)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        // --- Vérification des champs pour afficher le bouton Save ---
        fun checkFields() {
            val questionFilled = editTextQuestion.text.toString().trim().isNotEmpty()
            val answerFilled   = editTextAnswer.text.toString().trim().isNotEmpty()
            buttonSave.visibility = if (questionFilled && answerFilled) View.VISIBLE else View.GONE
        }

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { checkFields() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        editTextQuestion.addTextChangedListener(watcher)
        editTextAnswer.addTextChangedListener(watcher)
    }
}