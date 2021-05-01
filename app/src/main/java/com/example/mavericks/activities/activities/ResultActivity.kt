package com.example.mavericks.activities.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RenderScript
import android.text.Html
import android.widget.Button
import com.example.mavericks.R
import com.example.mavericks.activities.models.Quiz
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_result.*
import java.util.prefs.AbstractPreferences

class ResultActivity : AppCompatActivity() {
    lateinit var quiz: Quiz
    lateinit var sharedPreferences:SharedPreferences
    lateinit var backToquiz:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setUpView()
        backToquiz=findViewById(R.id.backToQuiz)
        backToquiz.setOnClickListener {
            val intent=Intent(this,Screen1::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun setUpView(){
        val quizData = intent.getStringExtra("Quiz")
        quiz = Gson().fromJson<Quiz>(quizData,Quiz::class.java)
        calculateScore()
        setAnswerView()
    }
    private fun setAnswerView(){
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries) {
            val question = entry.value
            builder.append("<font color'#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${question.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            txtAnswer.text = Html.fromHtml(builder.toString());
        }

    }
    private fun calculateScore(){
        var score=0
        for (entry in quiz.questions.entries){
            val question = entry.value
            if(question.answer == question.userAnswer){
                score+=10
            }
        }
        txtScore.text="Your Score : $score"
        sharedPreferences=getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.apply {
            putInt("Hscore",score)
        }.apply()



    }

}