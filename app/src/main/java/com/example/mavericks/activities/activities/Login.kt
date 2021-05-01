package com.example.mavericks.activities.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mavericks.R
import com.facebook.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import org.json.JSONObject
import java.lang.Exception


@Suppress("DEPRECATION")
class Login:AppCompatActivity() {
    private val RC_SIGN_IN: Int = 120
    lateinit var userLogin: Button
    lateinit var gso: GoogleSignInOptions
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var mAuth: FirebaseAuth
    lateinit var newUserRegister: TextView
    lateinit var useremail: EditText
    lateinit var password: EditText
    lateinit var forgotPassword: TextView
    lateinit var googleBtn: ImageView
    lateinit var ref: DatabaseReference
    lateinit var mProgressDialog: ProgressDialog
//    lateinit var callBackManager: CallbackManager
//    lateinit var fbBtn: com.facebook.login.widget.LoginButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userLogin = findViewById(R.id.btn_login)
        newUserRegister = findViewById(R.id.goToRegister)
        useremail = findViewById(R.id.inEmail)
        password = findViewById(R.id.inPassword)
        forgotPassword = findViewById(R.id.ForgotPassword)
        googleBtn = findViewById(R.id.googleLogin)
        mAuth = FirebaseAuth.getInstance()
//        fbBtn=findViewById(R.id.LoginButton)
//        FacebookSdk.sdkInitialize(this)
        // Initialize Facebook Login button

//        callBackManager = CallbackManager.Factory.create()
//        fbBtn.setReadPermissions("email")
//        fbBtn.setOnClickListener {
//            fbSignin()
//        }



        newUserRegister.setOnClickListener {
            val startIntent = Intent(this, Register::class.java)
            startActivity(startIntent)
        }
//        fbBtn.setOnClickListener {
//            fbBtn.setReadPermissions("email","public_profile")
//            callBackManager= CallbackManager.Factory.create()
//
//            fbBtn.registerCallback(callBackManager, object : FacebookCallback<LoginResult> {
//                override fun onSuccess(loginResult: LoginResult) {
//                    Toast.makeText(this@Login,"Success",Toast.LENGTH_LONG).show()
//                    handleFacebookAccessToken(loginResult.accessToken)
//                }
//
//                override fun onCancel() {
//                }
//
//                override fun onError(error: FacebookException) {
//                }
//            })
//            // ...
//             fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//                super.onActivityResult(requestCode, resultCode, data)
//
//                // Pass the activity result back to the Facebook SDK
//                callBackManager.onActivityResult(requestCode, resultCode, data)
//            }
//
//        }
        // Configure Google Sign In
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleBtn.setOnClickListener {
            signIn()
        }

        forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
            finish()
        }
        mAuth = FirebaseAuth.getInstance()

        mProgressDialog = ProgressDialog(this)
        userLogin.setOnClickListener {
            val email = useremail.text.toString().trim()
            val pass = password.text.toString().trim()
            if (TextUtils.isEmpty(email)) {
                useremail.error = "Enter Email"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(pass)) {
                password.error = "Enter password"
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                useremail.error = "Enter Valid Email"
                return@setOnClickListener
            }
            loginUser(email, pass)
        }
    }

    private fun loginUser(Email: String, Password: String) {
        mProgressDialog.setMessage("Loading Please Wait..")
        mProgressDialog.show()
        mAuth.signInWithEmailAndPassword(Email, Password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Successfully login", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, Screen1::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this, "Wrong Password",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                mProgressDialog.dismiss()
            }

    }

    private fun forgotpassword(usermail: EditText) {
        if (usermail.text.toString().trim().isEmpty()) {
            return
        }
//
        if (!Patterns.EMAIL_ADDRESS.matcher(usermail.text.toString().trim()).matches()) {
            return
        }
        mAuth.sendPasswordResetEmail(usermail.text.toString().trim())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        callBackManager.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e)
            }
        }
    }
//    private fun handleFacebookAccessToken(token: AccessToken) {
//        val credential = FacebookAuthProvider.getCredential(token.token)
//        mAuth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    val fbIntent = Intent(this,
//                        Screen1::class.java)
//                    startActivity(fbIntent)
//                    finish()
//
//                    val user = mAuth.currentUser
//                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
//                }
//            }
//    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show()
                    val googleintent = Intent(
                        this,
                        Screen1::class.java
                    )
                    startActivity(googleintent)
                    finish()
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show()

                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {

            updateUI(currentUser)
        }
    }

    fun updateUI(currentUser: FirebaseUser?) {

    }
//    private fun fbSignin(){
//        fbBtn.registerCallback(callBackManager,object :FacebookCallback<LoginResult>{
//            override fun onSuccess(result: LoginResult?) {
//                handleFacebookAccessToken(result!!.accessToken)
//            }
//
//            override fun onCancel() {
//                TODO("Not yet implemented")
//            }
//
//            override fun onError(error: FacebookException?) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }
//    private fun handleFacebookAccessToken(token: AccessToken) {
//        val credential = FacebookAuthProvider.getCredential(token.token)
//        mAuth.signInWithCredential(credential)
//            .addOnFailureListener { e ->
//                Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
//            }
//                .addOnSuccessListener {result->
//                    val email:String=result.user.email
//                    Toast.makeText(this,"Login Success",Toast.LENGTH_LONG).show()
//
//                }
//    }



}