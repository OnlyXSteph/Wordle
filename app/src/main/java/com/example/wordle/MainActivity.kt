package com.example.wordle

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.guessButton)
        val guessNum = findViewById<TextView>(R.id.guessText)
        val correctWord = findViewById<TextView>(R.id.wordText)
        val results = findViewById<TextView>(R.id.resultsText)
        val word = findViewById<EditText>(R.id.guessEntry) as EditText

        val wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        button.setOnClickListener {

            if (counter <= 2) {

                hideKeyboard()
                counter++

                guessNum.append("Guess $counter\n")
                guessNum.append("Guess $counter Check\n")

                val input = word.text.toString().uppercase()
                results.append (input+"\n")

                results.append(checkGuess(input, wordToGuess)+"\n")

                if (wordToGuess == input) {

                    Toast.makeText(it.context, "CORRECT", Toast.LENGTH_LONG).show()
                    button.isEnabled = false

                    correctWord.text = wordToGuess
                    correctWord.visibility = View.VISIBLE

                }
            }

            if (counter >= 3) {

                correctWord.text = wordToGuess
                correctWord.visibility = View.VISIBLE
                button.isEnabled = false

            }
        }
    }

    private fun checkGuess(guess: String, wordToGuess: String) : String {

        var result = ""

        for (i in 0..3) {

            if (guess[i] == wordToGuess[i]) {

                result += "O"

            }
            else if (guess[i] in wordToGuess) {

                result += "+"

            }
            else {

                result += "X"

            }
        }

        return result

    }

    fun Fragment.hideKeyboard() {

        view?.let { activity?.hideKeyboard(it) }

    }
    fun Activity.hideKeyboard() {

        hideKeyboard(currentFocus ?: View(this))

    }
    fun Context.hideKeyboard(view: View) {

        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

    }
}