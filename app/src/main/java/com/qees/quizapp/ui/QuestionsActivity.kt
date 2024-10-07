package com.qees.quizapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.qees.quizapp.R
import com.qees.quizapp.model.Question
import com.qees.quizapp.utils.Constants

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var progressBar: ProgressBar
    private lateinit var flagImage: ImageView
    private lateinit var progressBarText: TextView
    private lateinit var questionText: TextView
    private lateinit var optionOne: TextView
    private lateinit var optionTwo: TextView
    private lateinit var optionThree: TextView
    private lateinit var optionFour: TextView
    private lateinit var checkButton: Button



    private var questionsCounter = 0
    private lateinit var questionList: MutableList<Question>
    private var selectedAnswer = 0
    private lateinit var currentQuestion: Question
    private  var answered = false
    private lateinit var name: String
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_questions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progressBar)
        flagImage = findViewById(R.id.image_flag)
        progressBarText = findViewById(R.id.text_view_progress)
        questionText = findViewById(R.id.question_text)
        optionOne = findViewById(R.id.text_option_one)
        optionTwo = findViewById(R.id.text_option_two)
        optionThree = findViewById(R.id.text_option_three)
        optionFour = findViewById(R.id.text_option_four)
        checkButton = findViewById(R.id.button_checked)


        optionOne.setOnClickListener(this)
        optionTwo.setOnClickListener(this)
        optionThree.setOnClickListener(this)
        optionFour.setOnClickListener(this)

        checkButton.setOnClickListener(this)


        questionList = Constants.getQuestion()
        Log.d("QuestionSize", "$(questionsList.size)")

        showNextQuestion()

        if(intent.hasExtra(Constants.USER_NAME)) {
            name = intent.getStringExtra(Constants.USER_NAME)!!
        }
    }


    @SuppressLint("SetTextI18n")
    private fun showNextQuestion() {

        if (questionsCounter < questionList.size){
            currentQuestion = questionList[questionsCounter]
            flagImage.setImageResource(currentQuestion.image)
            progressBar.progress = questionsCounter
            progressBarText.text = "${questionsCounter + 1}/${progressBar.max}"
            questionText.text = currentQuestion.question
            optionOne.text = currentQuestion.optionOne
            optionTwo.text = currentQuestion.optionTwo
            optionThree.text = currentQuestion.optionThree
            optionFour.text = currentQuestion.optionFour

            resetOption()

            questionsCounter++
            answered = false
            checkButton.text = "CHECK"
        }else{
            checkButton.text = "FINISH"
            // start activity here

            Intent(this, ResultActivity::class.java).also{
                it.putExtra(Constants.USER_NAME,name)
                it.putExtra(Constants.SCORE,score)
                it.putExtra(Constants.TOTAL_QUESTIONS,questionList.size)
                startActivity(it)
            }
        }

    }

    private fun resetOption(){

        val options = mutableListOf<TextView>()
        options.add(optionOne)
        options.add(optionTwo)
        options.add(optionThree)
        options.add(optionFour)

        for (option in options){
            option.setTextColor(Color.parseColor("#7a8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.text_option_one -> {
                selectedOption(optionOne, 1)
            }

            R.id.text_option_two -> {
                selectedOption(optionTwo, 2)
            }

            R.id.text_option_three -> {
                selectedOption(optionThree, 3)
            }

            R.id.text_option_four -> {
                selectedOption(optionFour, 4)
            }

            R.id.button_checked -> {
                if(!answered) {
                   checkAnswer()
                }else{
                    showNextQuestion()
                }
                selectedAnswer = 0


            }
        }
    }

    private fun selectedOption(textView: TextView, selectedOptionNumber: Int){
        resetOption()
        selectedAnswer = selectedOptionNumber

        textView.setTextColor(Color.parseColor("#363a43"))
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }


    private  fun checkAnswer() {
        if(!::currentQuestion.isInitialized) {
            Log.e("QuestionsActivity", "currentQuestion is not initialized.")
            return
        }
        answered = true


        if(selectedAnswer
            == currentQuestion.correctAnswer) {
            score++
            highlightedAnswer(selectedAnswer)

        }else{
            when(selectedAnswer) {
                1 -> {
                    optionOne.background =
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.wrong_option_border_bg
                        )
                }

                2 -> {
                    optionTwo.background =
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.wrong_option_border_bg
                        )
                }
                3 -> {
                    optionThree.background =
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.wrong_option_border_bg
                        )
                }
                4 -> {
                    optionFour.background =
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.wrong_option_border_bg
                        )
                }
            }


        }
        checkButton.text = "NEXT"
        showSolution()

    }

    private fun showSolution() {
        selectedAnswer = currentQuestion.correctAnswer

        highlightedAnswer(selectedAnswer)


    }

    private fun highlightedAnswer(answer: Int) {
        when(answer) {

            1 -> {
                optionOne.background =
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.correct_option_border_bg
                    )
            }

            2 -> {
                optionTwo.background =
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.correct_option_border_bg
                    )
            }
            3 -> {
                optionThree.background =
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.correct_option_border_bg
                    )
            }
            4 -> {
                optionFour.background =
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.correct_option_border_bg
                    )
            }
        }
    }

}