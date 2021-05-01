package com.example.mavericks.activities.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mavericks.R
import com.example.mavericks.activities.adapters.QuizAdapter
import com.example.mavericks.activities.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.screen1.*
import java.text.SimpleDateFormat
import java.util.*

class Screen1:AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var quizAdapter:QuizAdapter
    private var quizList= mutableListOf<Quiz>()
    lateinit var firestore:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen1)

        setupView()
    }
    fun setupView(){
        setUpFireStore()
        setUpDrawrLayout()
        setUpRecyclerView()
        setUpDatePicker()

    }

    fun setUpDatePicker() {
        btn_datepicker.setOnClickListener{
            val datePicker=MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER",datePicker.headerText)
                val dateFormat = SimpleDateFormat("dd-mm-yyyy")
                val date = dateFormat.format(Date(it))
                val intent = Intent(this,QuestionActivity::class.java)
                intent.putExtra("Date",date)
                startActivity(intent)

            }
            datePicker.addOnNegativeButtonClickListener{
                Log.d("DATEPICKER",datePicker.headerText)


            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER","Date Picker Cancelled")

            }
        }

    }

    fun setUpRecyclerView(){
        quizAdapter= QuizAdapter(this,quizList)
        quizRecylerView.layoutManager=GridLayoutManager(this,2)
        quizRecylerView.adapter=quizAdapter
    }
    fun setUpDrawrLayout(){
        setSupportActionBar(appbar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()
        navigation_view.setNavigationItemSelectedListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            drawer.closeDrawers()
            true
        }
    }
    fun setUpFireStore(){
        firestore= FirebaseFirestore.getInstance()
        val collectionReference=firestore.collection("quizzes")
        collectionReference.addSnapshotListener{value,error->
            if(value==null || error!=null){
                Toast.makeText(this,"Error in fetching data",Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            Log.d("DATA",value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            quizAdapter.notifyDataSetChanged()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){

        }
        return super.onOptionsItemSelected(item)
    }
}