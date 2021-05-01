package com.example.mavericks.activities.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mavericks.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_result.*

class ProfileActivity : AppCompatActivity() {
    lateinit var mAuth:FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mAuth= FirebaseAuth.getInstance()
        txtEmail.text=mAuth.currentUser?.email
        btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        sharedPreferences = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)
        val savedInt=sharedPreferences.getInt("Hscore",0)
        Highest_score.text="Your Highest Score : $savedInt"


    }
}