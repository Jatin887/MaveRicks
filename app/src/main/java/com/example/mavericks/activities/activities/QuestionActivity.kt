package com.example.mavericks.activities.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mavericks.R
import com.example.mavericks.activities.adapters.OptionAdapter
import com.example.mavericks.activities.models.Questions
import com.example.mavericks.activities.models.Quiz
import com.facebook.internal.Mutable
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.forgot.*

class QuestionActivity : AppCompatActivity() {
    lateinit var firestore:FirebaseFirestore
    var quizzes:MutableList<Quiz>?=null
    var questions: MutableMap<String,Questions>?=null
    var index =1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setUpFireStore()
        setUpEventListner()

    }

    private fun setUpEventListner() {
        Btn_previous.setOnClickListener{
            index--
            bindViews()
        }
        Btn_next.setOnClickListener{
            index++
            bindViews()
        }
        Btn_submit.setOnClickListener{
            val intent = Intent(this,ResultActivity::class.java)
            val json=Gson().toJson(quizzes!![0])
            intent.putExtra("Quiz",json)
            startActivity(intent)
            finish()

        }
    }

    private fun bindViews(){
        Btn_submit.visibility=View.GONE
        Btn_next.visibility=View.GONE
        Btn_previous.visibility=View.GONE
        if (index==1){ // first question
            Btn_next.visibility=View.VISIBLE
        }
        else if(index== questions!!.size ){ // last question
            Btn_previous.visibility=View.VISIBLE
            Btn_submit.visibility=View.VISIBLE
        }
        else{ // Middle
            Btn_next.visibility=View.VISIBLE
            Btn_previous.visibility=View.VISIBLE
        }
        val question=questions!!["question$index"]
        question?.let {
            description.text = it.description
            val optionAdapter=OptionAdapter(this,it)
            option_list.layoutManager=LinearLayoutManager(this)
            option_list.adapter = optionAdapter
            option_list.setHasFixedSize(true)
        }


    }
    private fun setUpFireStore(){
        firestore= FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("Date")
        if (date!=null){
            firestore.collection("quizzes").whereEqualTo("title",date)
                    .get()
                    .addOnSuccessListener {
                        if(it!=null && !it.isEmpty ){
                            quizzes= it.toObjects(Quiz::class.java)
                            questions = quizzes!![0].questions
                            bindViews()
                        }
                    }
        }


    }
}