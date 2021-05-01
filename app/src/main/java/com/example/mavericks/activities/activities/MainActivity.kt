package com.example.mavericks.activities.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.mavericks.R
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var start:Button
    lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start=findViewById(R.id.Button)
        mAuth= FirebaseAuth.getInstance()
        if(mAuth.currentUser!=null){
            Toast.makeText(this,"User is already logged in!",Toast.LENGTH_LONG).show()
            redirect("MAIN")
        }
        start.setOnClickListener{
            redirect("LOGIN")
        }
    }
    private fun redirect(name:String) {
        val intent= when(name){
            "LOGIN"->Intent(this, Login::class.java)
            "MAIN"-> Intent(this, Screen1::class.java)
            else->throw  Exception("no path exists")
        }
        startActivity(intent)
        finish()
    }
}