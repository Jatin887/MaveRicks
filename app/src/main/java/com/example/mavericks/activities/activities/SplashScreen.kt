package com.example.mavericks.activities.activities
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mavericks.R


@Suppress("DEPRECATION")
class SplashScreen:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)
//        val rotate_clockwise=AnimationUtils.loadAnimation(this,R.anim.rotate_clockwise)
//        val rotate_anticlokwise=AnimationUtils.loadAnimation(this,R.anim.anti_clockwise)
        val top=AnimationUtils.loadAnimation(this, R.anim.top)
        val bottom=AnimationUtils.loadAnimation(this, R.anim.bottom)
        val logo=findViewById<TextView>(R.id.Logo)
        val logoImg=findViewById<ImageView>(R.id.LogoImg)
        logoImg.startAnimation(top)
        logo.startAnimation(bottom)


        val timeout = 4000
        val homeIntent=Intent(this, MainActivity::class.java)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, timeout.toLong())

    }

}