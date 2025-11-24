package com.example.flashcardapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
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

        val buttonCancel = findViewById<Button>(R.id.button_cancel)
        buttonCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val editTextQuestion = findViewById<EditText>(R.id.edit_text_question)
        val editTextAnswer = findViewById<EditText>(R.id.edit_text_answer)
        val buttonSave = findViewById<Button>(R.id.button_save)

        // Fonction pour vérifier si les deux champs sont remplis
        fun checkFields() {
            val questionFilled = editTextQuestion.text.toString().trim().isNotEmpty()
            val answerFilled = editTextAnswer.text.toString().trim().isNotEmpty()

            if (questionFilled && answerFilled) {
                buttonSave.visibility = View.VISIBLE
            } else {
                buttonSave.visibility = View.GONE
            }
        }

// Ajouter un TextWatcher sur les deux EditText
        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkFields()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        editTextQuestion.addTextChangedListener(watcher)
        editTextAnswer.addTextChangedListener(watcher)


    }
}