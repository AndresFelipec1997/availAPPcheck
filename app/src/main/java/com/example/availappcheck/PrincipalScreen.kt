package com.example.availappcheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_principal_screen.*

class PrincipalScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_screen)

        val bundle: Bundle? = intent.extras
        var email: String? = bundle?.getString("email")

        setup(email ?: "")

        registrarnegocioboton.setOnClickListener {

            val intent= Intent(this,registrarNegocio::class.java)
            startActivity(intent)
        }

        cerrar.setOnClickListener() {

            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }


    }

    private fun setup(email: String?) {
        title = "inicio"
        emailView.text = email



    }




}