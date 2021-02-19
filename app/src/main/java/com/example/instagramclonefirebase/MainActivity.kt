package com.example.instagramclonefirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

       val  user = auth.currentUser

        if(user != null)
        {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
            finish()
        }



        register_button.setOnClickListener {
            val email = username_text.text.toString()
            val password = password_text.text.toString()
            registerUser(email,password)
        }
        signin_button.setOnClickListener {
            val email = username_text.text.toString()
            val password = password_text.text.toString()
            signinUser(email,password)
        }
    }

    fun registerUser(email : String, password :String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task ->
                if(task.isSuccessful){
                    Toast.makeText(this,"User Created",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity2::class.java)
                    startActivity(intent)
                    finish()


                }
        }.addOnFailureListener { e->

            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }
    }
    fun signinUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ task->

                if(task.isSuccessful){
                    Toast.makeText(this,"Succes",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity2::class.java)
                    startActivity(intent)
                    finish()


                }
        }.addOnFailureListener {
            e-> Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
        }

    }
}