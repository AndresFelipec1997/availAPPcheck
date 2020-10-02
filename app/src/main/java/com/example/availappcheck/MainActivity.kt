package com.example.availappcheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        inicio.setOnClickListener(){

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)


        }

    }

    fun clicRegsitrar(v: View){
        val intent = Intent(this,RegisterActivity2::class.java)
        startActivity(intent)

    }




}