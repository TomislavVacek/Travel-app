package com.travel.croatia

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.travel.croatia.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    var binding: ActivityLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {  // onCreate() metodi se stvara binding objekt koji se koristi za pristup elementima korisničkog sučelja
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        FirebaseApp.initializeApp(this)
        binding!!.signIn.setOnClickListener { signIn() }  //OnClickListener za gumb za prijavu signIn
        binding!!.reg.setOnClickListener { register() }
        binding!!.register.setOnClickListener { //create method to update views this will help to reduce the code and make it reusable
            updateView(binding!!.loginView, binding!!.registerView)
        }
        binding!!.login.setOnClickListener { //Used the same function
            updateView(binding!!.registerView, binding!!.loginView)
        }
    }

    private fun signIn() {   //se poziva kada korisnik klikne na gumb za prijavu,,Ova metoda provjerava jesu li uneseni podaci za prijavu ispravn
        val email = binding!!.email.text.toString().trim()
        val pass = binding!!.password.text.toString().trim()
        //Verifying Details
        if (TextUtils.isEmpty(email)) {
            binding!!.email.error = "Please Enter Registered Email"
        } else if (TextUtils.isEmpty(pass)) {
            binding!!.password.error = "Please Enter Password"
        } else {
            //Checking if the User is Registered or not
            binding!!.loading.visibility = View.VISIBLE
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding!!.loading.visibility = View.GONE
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    }
                }.addOnFailureListener {
                    binding!!.loading.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, "User Not Exist", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun register() {
        val name = binding!!.name.text.toString().trim()
        val email = binding!!.regEmil.text.toString().trim()
        val pass = binding!!.regPassword.text.toString().trim()
        val confPass = binding!!.regConfPassword.text.toString().trim()

        //Verifying All the Details Entered By User
        if (TextUtils.isEmpty(name)) {
            binding!!.name.error = "Please Enter Full Name"
        } else if (TextUtils.isEmpty(email) || !email.endsWith(".com") || !email.contains("@")) {
            binding!!.regEmil.error = "Please Enter Valid Email Address"
        } else if (TextUtils.isEmpty(pass)) {
            binding!!.regPassword.error = "Please Enter Password"
        } else if (TextUtils.isEmpty(confPass)) {
            binding!!.regConfPassword.error = "Pleas Retype Password"
        } else if (pass != confPass) {
            binding!!.regPassword.error = "Password Not Matched"
            binding!!.regConfPassword.error = "Password not Matched"
            binding!!.regConfPassword.setText("")
        } else {
            binding!!.loading.visibility = View.VISIBLE
            //Creating user and Storing the Details in Firebase
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    val id = FirebaseAuth.getInstance().currentUser!!.uid
                    val model = RegisterModel(id, name, email, pass)
                    FirebaseDatabase.getInstance().reference.child("users").child(id)
                        .setValue(model)
                        .addOnCompleteListener {
                            binding!!.loading.visibility = View.GONE
                            Toast.makeText(
                                this@LoginActivity,
                                "User Registered",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }.addOnFailureListener {
                            binding!!.loading.visibility = View.GONE
                            Toast.makeText(
                                this@LoginActivity,
                                "Unable to Store Information",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }
                }.addOnFailureListener {
                    binding!!.loading.visibility = View.GONE
                    Toast.makeText(this@LoginActivity, "User Already Exist", Toast.LENGTH_SHORT).show()
                }
        }
    }
//animacija za reganje,loganje
    private fun updateView(hideThis: ConstraintLayout, showThis: ConstraintLayout) {
        val hide = AnimationUtils.loadAnimation(this, R.anim.right_to_left)
        val show = AnimationUtils.loadAnimation(this, R.anim.left_to_right)
        hideThis.startAnimation(hide)
        hideThis.visibility = View.GONE
        showThis.visibility = View.VISIBLE
        showThis.startAnimation(show)
    }

//poziva kada se aktivnost prikaže na zaslonu. Ako je korisnik već prijavljen, preusmjerava se na glavni zaslon aplikacije.
    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            startActivity(
                Intent(this@LoginActivity, MainActivity::class.java)
            )
        }
    }
    override fun onBackPressed() {  //aktivnost se zatvara
        finishAffinity()
    }
}