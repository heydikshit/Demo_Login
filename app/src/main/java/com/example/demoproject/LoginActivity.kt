package com.example.demoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.demoproject.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        if(firebaseAuth.currentUser!=null){
            Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }

        binding.btnLogin.setOnClickListener {
            login()
        }
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun redirect(name:String){
        val intent = when (name){
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("no path")
        }
        startActivity(intent)
        finish()
    }

    private fun login(){
        val email = binding.etEmailAddress.text.toString()
        val password = binding.etPassword.text.toString()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    Toast.makeText(this, "Error Logging in", Toast.LENGTH_SHORT).show()
                }
            }
    }
}