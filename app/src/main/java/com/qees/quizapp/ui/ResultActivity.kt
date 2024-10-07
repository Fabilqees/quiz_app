 package com.qees.quizapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.qees.quizapp.MainActivity
import com.qees.quizapp.R
import com.qees.quizapp.R.id
import com.qees.quizapp.utils.Constants

 class ResultActivity : AppCompatActivity() {
    private lateinit var textViewScore: TextView
    private lateinit var textViewName: TextView
    private lateinit var finishButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewScore = findViewById(R.id.textview_score)
        textViewName = findViewById(R.id.textview_name)
        finishButton = findViewById(R.id.finish_button)

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val score = intent.getIntExtra(Constants.SCORE, 0)
        val name = intent.getStringExtra(Constants.USER_NAME)

        textViewScore.text = "Your score is $score out of $totalQuestions"
        textViewName.text = name

        finishButton.setOnClickListener {
            Intent(this, MainActivity::class.java).also{
                startActivity(it)
            }
        }
    }
}